package com.example.springresponseentitytest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class ApiController {
    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("response-entity")
    public ResponseEntity<?> responseEntity() {
        return new ResponseEntity<>("response-entity", HttpStatus.OK);
    }

    @GetMapping("redirect-view")
    public void redirectView(HttpServletResponse response) throws IOException {
        System.out.println("redirect-view");
        response.sendRedirect("http://localhost:8080/basic/home");
    }

    @GetMapping("forward-view")
    public void forwardView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("forward-view");
        request.getSession().getServletContext().getRequestDispatcher("/api/redirect-view").forward(request, response);
    }
}