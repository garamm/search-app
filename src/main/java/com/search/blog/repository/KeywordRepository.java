package com.search.blog.repository;

import com.search.blog.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByKeyword(String keyword);
    List<Keyword> findTop10ByOrderByCountDesc();

}
