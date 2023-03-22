package com.search.blog.service;

import com.search.blog.entity.Keyword;
import com.search.blog.repository.KeywordRepository;
import com.search.blog.vo.BlogResp;
import com.search.blog.exception.ErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Value("${kakao.url}")
    private String kakaoUrl;

    @Value("${kakao.auth}")
    private String kakaoAuth;

    @Value("${naver.url}")
    private String naverUrl;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Autowired
    KeywordRepository repository;


    private HttpHeaders getKakaoHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", kakaoAuth);
        return headers;
    }

    private String makeKakaoBuilder(String query, String sort, int page, int size) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(kakaoUrl).queryParam("query", query)
                .queryParam("sort", sort).queryParam("page", page).queryParam("size", size);
        return builder.toUriString();
    }

    private HttpHeaders getNaverHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverClientSecret);
        return headers;
    }

    private String makeNaverBuilder(String query, String sort, int page, int size) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(naverUrl).queryParam("query", query)
                .queryParam("sort", sort).queryParam("start", page).queryParam("display", size);
        return builder.toUriString();
    }

    public <T> ResponseEntity<T> getKakaoResult(String query, String sort, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpEntity<String> entity = new HttpEntity<>(getKakaoHeader());
        ResponseEntity<BlogResp> result = restTemplate.exchange(makeKakaoBuilder(query, sort, page, size), HttpMethod.GET, entity, BlogResp.class);
        return (ResponseEntity<T>) result;
    }

    public <T> ResponseEntity<T> getNaverResult(String query, String sort, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpEntity<String> entity = new HttpEntity<>(getNaverHeader());
        String sortStr = "sim";
        if (sort.equals("recency")) {
            sortStr = "date";
        }
        ResponseEntity<String> result = restTemplate.exchange(makeNaverBuilder(query, sortStr, page, size), HttpMethod.GET, entity, String.class);
        return (ResponseEntity<T>) result;
    }

    @Transactional
    @Override
    public <T> ResponseEntity<T> searchKeyword(String query, String sort, int page, int size) {

        updateKeywordData(query);

        ResponseEntity<T> kakaoResult = getKakaoResult(query, sort, page, size);
        if (kakaoResult.getStatusCode() == HttpStatus.OK) {
            return kakaoResult;
        }

        ResponseEntity<T> naverResult = getNaverResult(query, sort, page, size);
        if (naverResult.getStatusCode() == HttpStatus.OK) {
            return naverResult;
        }

        ErrorResp errorResp = new ErrorResp(500, "서버 응답 오류");
        return (ResponseEntity<T>) new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public void updateKeywordData(String query) {
        List<Keyword> list = repository.findByKeyword(query);
        if (list.size() == 0) {
            repository.save(new Keyword(query));
        } else {
            list.get(0).setCount(list.get(0).getCount() + 1L);
            repository.save(list.get(0));
        }
    }

    @Override
    public List<Keyword> findTopTen() {
        return repository.findTop10ByOrderByCountDesc();
    }

}
