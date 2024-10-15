package com.fastcampus.shyoon.part3;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    // redis 세션 클러스터링을 사용하여
    // 분산환경에서도 세션이 공유될 수 있도록 함

    @GetMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        session.setAttribute("name", name);
        return "login";
    }

    @GetMapping("/myName")
    public String myName(HttpSession session) {
        return (String) session.getAttribute("name");
    }
}
