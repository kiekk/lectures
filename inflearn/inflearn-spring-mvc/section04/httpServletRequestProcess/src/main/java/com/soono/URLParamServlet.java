package com.soono;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "urlParamServlet", urlPatterns = "/urlParam")
public class URLParamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 한글 출력 가능하도록 설정
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // (1) 단일 파라미터 조회 예시: ?username=홍길동
        String username = request.getParameter("username");
        out.println("단일 파라미터 username = " + username);

        // (2) 동일 이름의 복수 파라미터 예시: ?item=사과&item=배&item=포도
        String[] items = request.getParameterValues("item");
        if (items != null) {
            out.println("복수 파라미터 item[] = " + Arrays.toString(items));
        } else {
            out.println("복수 파라미터 item[]가 없습니다.");
        }

        // (3) 모든 파라미터 맵 형태로 조회
        Map<String, String[]> paramMap = request.getParameterMap();
        String allParams = paramMap.entrySet().stream()
                .map(e -> e.getKey() + "=" + Arrays.toString(e.getValue()))
                .collect(Collectors.joining(", "));
        out.println("모든 파라미터: " + allParams);
    }
}

