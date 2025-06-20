package com.example.springresponseentitytest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api")
public class ApiController {

    // RequestResponseBodyMethodProcessor
    @GetMapping("home")
    public String home() {
        return "home";
    }

    // HttpEntityMethodProcessor
    @GetMapping("response-entity")
    public ResponseEntity<?> responseEntity() {
        return new ResponseEntity<>("response-entity", HttpStatus.OK);
    }

    // No Handler - Just Redirect??
    @GetMapping("redirect-view-v1")
    public void redirectViewV1(HttpServletResponse response) throws IOException {
        System.out.println("redirect-view-v1");
        response.sendRedirect("http://localhost:8080/basic/home");
    }

    // HttpEntityMethodProcessor
    @GetMapping("redirect-view-v2")
    public ResponseEntity<?> redirectViewV2() {
        System.out.println("redirect-view-v2");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:8080/basic/home"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // ViewMethodReturnValueHandler
    @GetMapping("redirect-view-v3")
    public RedirectView redirectViewV3() {
        System.out.println("redirect-view-v3");
        return new RedirectView("http://localhost:8080/basic/home");
    }

    // No Handler - Just Forward??
    @GetMapping("forward-view")
    public void forwardView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("forward-view");
        request.getSession().getServletContext().getRequestDispatcher("/api/redirect-view-v1").forward(request, response);
    }
}