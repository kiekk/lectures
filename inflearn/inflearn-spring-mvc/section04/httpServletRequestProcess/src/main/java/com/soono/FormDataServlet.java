package com.soono;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "formDataServlet", urlPatterns = "/formTest")
public class FormDataServlet extends HttpServlet {

    // (1) GET 방식
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("=== [GET] FORM 데이터 처리 예제 ===");

        // 폼에 입력된 파라미터 조회
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        out.println("이름: " + name);
        out.println("나이: " + age);

        // 다른 파라미터들도 getParameterMap() 등으로 확인 가능
        Map<String, String[]> paramMap = request.getParameterMap();
        String allParams = paramMap.entrySet().stream()
                .map(e -> e.getKey() + "=" + String.join("/", e.getValue()))
                .collect(Collectors.joining(", "));
        out.println("전체 파라미터: " + allParams);
    }

    // (2) POST 방식
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // POST로 전달된 폼 데이터도 getParameter()로 동일하게 처리 가능
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("=== [POST] FORM 데이터 처리 예제 ===");

        String name = request.getParameter("name");
        String age = request.getParameter("age");

        out.println("이름: " + name);
        out.println("나이: " + age);

        // 전체 파라미터 조회
        Map<String, String[]> paramMap = request.getParameterMap();
        String allParams = paramMap.entrySet().stream()
                .map(e -> e.getKey() + "=" + String.join("/", e.getValue()))
                .collect(Collectors.joining(", "));
        out.println("전체 파라미터: " + allParams);
    }
}
