package com.search.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count;
    private LocalDateTime lastUpdateTime;

    public Keyword(String keyword) {
        this.keyword = keyword;
        this.count = 1L;
        this.lastUpdateTime = LocalDateTime.now();
    }

}
