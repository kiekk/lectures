package com.example.requestbody.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestType {
    GET("get"),
    POST("post");

    private final String method;
}
