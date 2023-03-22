package com.search.blog.service;

import com.search.blog.entity.Keyword;
import com.search.blog.repository.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class BlogServiceImplTests {

    @Autowired
    private BlogServiceImpl service;

    @Autowired
    KeywordRepository repository;

    @Test()
    @DisplayName("블로그 검색 - 카카오")
    public void blogSearchKakao() {
        String query = "퍼즐";
        String sort = "accuracy";
        int page = 1;
        int size = 3;
        ResponseEntity result = service.getKakaoResult(query, sort, page, size);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("블로그 검색 - 네이버")
    public void blogSearchNaver() {
        String query = "퍼즐";
        String sort = "accuracy";
        int page = 1;
        int size = 3;
        ResponseEntity result = service.getNaverResult(query, sort, page, size);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("검색 키워드 저장")
    public void updateKeywordData() {
        String query = "퍼즐";
        List<Keyword> before = repository.findByKeyword(query);
        service.updateKeywordData(query);
        List<Keyword> after = repository.findByKeyword(query);

        assertThat(before.size() + 1).isEqualTo(after.size());
    }

    @Test
    @DisplayName("인기검색어 조회")
    public void findTopTen() {
        service.updateKeywordData("종이접기");

        service.updateKeywordData("게임");
        service.updateKeywordData("게임");

        service.updateKeywordData("개발");
        service.updateKeywordData("개발");
        service.updateKeywordData("개발");

        List<Keyword> list = service.findTopTen();
        assertThat(list.get(0).getKeyword()).isEqualTo("개발");
    }

}
