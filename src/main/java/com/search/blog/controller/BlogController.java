package com.search.blog.controller;

import com.search.blog.entity.Keyword;
import com.search.blog.exception.CustomException;
import com.search.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService service;

    @GetMapping("/search")
    public <T> ResponseEntity<T> searchBlog(
            @RequestParam @NotEmpty(message = "검색어를 입력해주세요.") @Valid String query,
            @RequestParam(required = false, defaultValue = "accuracy") String sort,
            @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(50) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(50) int size
    ) {
        return service.searchKeyword(query, sort, page, size);
    }

    @GetMapping("/top")
    public List<Keyword> find() {
        return service.findTopTen();
    }

}
