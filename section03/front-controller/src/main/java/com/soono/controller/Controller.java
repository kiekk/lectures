package com.soono.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Controller {
    /**
     * 요청을 처리하고, 뷰 경로(viewPath)나 리다이렉트 주소 등을 반환
     */
    String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
