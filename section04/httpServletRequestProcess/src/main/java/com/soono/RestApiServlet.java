package com.soono;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "restApiServlet", urlPatterns = "/restTest")
public class RestApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String contentType = request.getContentType();
        out.println("수신된 Content-Type: " + contentType);

        if (contentType == null) {
            out.println("Content-Type이 없습니다. 처리 불가능");
            return;
        }

        if (contentType.startsWith("text/plain")) {
            // (1) Plain Text 처리
            handlePlainText(request, out);
        } else if (contentType.startsWith("application/json")) {
            // (2) JSON 처리
            handleJson(request, out);
        } else {
            out.println("지원하지 않는 Content-Type입니다.");
        }
    }

    // Plain Text 데이터 처리
    private void handlePlainText(HttpServletRequest request, PrintWriter out) throws IOException {
        // 문자 스트림
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        out.println("[PlainText] 바디 내용: " + sb.toString());
    }

    // JSON 데이터 처리
    private void handleJson(HttpServletRequest request, PrintWriter out) throws IOException {
        // 바이너리 스트림으로 받기
        InputStream inputStream = request.getInputStream();
        // Jackson ObjectMapper 사용하여 Map 형태로 변환 (또는 DTO)
        Map<?, ?> jsonMap = objectMapper.readValue(inputStream, HashMap.class);

        out.println("[JSON] 파싱된 데이터(Map): " + jsonMap.toString());
        if (jsonMap.containsKey("name")) {
            out.println("이름: " + jsonMap.get("name"));
        }
        if (jsonMap.containsKey("age")) {
            out.println("나이: " + jsonMap.get("age"));
        }
    }
}
