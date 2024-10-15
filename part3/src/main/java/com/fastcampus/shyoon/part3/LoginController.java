package com.fastcampus.shyoon.part3;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {

    // 예시를 위해 실제 DB 대신 로컬 변수 사용
    HashMap<String, String> sessionMap = new HashMap<>();

    @GetMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        sessionMap.put(session.getId(), name);
        return "login";
    }

    @GetMapping("/myName")
    public String myName(HttpSession session) {
        return sessionMap.get(session.getId());
    }
}
