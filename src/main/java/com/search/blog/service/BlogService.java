package com.search.blog.service;

import com.search.blog.entity.Keyword;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    <T> ResponseEntity<T> searchKeyword(String query, String sort, int page, int size);
    List<Keyword> findTopTen();

}
