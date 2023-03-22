package com.search.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class BlogResp {
    Meta meta;
    ArrayList<Document> documents;

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("total_count")
        int totalCount;
        @JsonProperty("pageable_count")
        int pageableCount;
        @JsonProperty("is_end")
        boolean isEnd;
    }

    @Getter
    @Setter
    public static class Document {
        String title;
        String contents;
        String url;
        String blogName;
        String thumbnail;
        ZonedDateTime datetime;

    }
}
