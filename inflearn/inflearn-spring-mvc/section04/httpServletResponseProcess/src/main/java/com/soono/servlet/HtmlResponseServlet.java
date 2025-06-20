package com.soono.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "htmlResponseServlet", urlPatterns = "/htmlResponse")
public class HtmlResponseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Content-Type 지정
        response.setContentType("text/html;charset=UTF-8");

        // PrintWriter 획득
        PrintWriter out = response.getWriter();

        // HTML 응답
        // """ 로 변경
        out.println("""
                <!DOCTYPE html>)
                <html>
                <head>
                    <meta charset='UTF-8' />
                    <title>HTML 응답 예제</title>
                </head>
                <body>
                    <h1>HTML 화면 처리 예제</h1>
                    <p>이 영역은 HTML 태그를 통해 표현됩니다.</p>
                </body>
                </html>
                """);

        // 자원 정리
        out.close();
    }
}
