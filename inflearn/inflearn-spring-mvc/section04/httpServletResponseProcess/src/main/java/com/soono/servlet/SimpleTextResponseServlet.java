package com.soono.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "simpleTextResponseServlet", urlPatterns = "/textResponse")
public class SimpleTextResponseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Content-Type 지정
        response.setContentType("text/plain;charset=UTF-8");

        // PrintWriter 획득
        PrintWriter out = response.getWriter();

        // 단순 텍스트 응답
        out.write("안녕하세요! 이것은 단순 텍스트 응답 예제입니다.");

        // 자원 정리
        out.close();
    }
}
