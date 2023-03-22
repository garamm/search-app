package com.search.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR(406, "Client Error"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String msg;
}