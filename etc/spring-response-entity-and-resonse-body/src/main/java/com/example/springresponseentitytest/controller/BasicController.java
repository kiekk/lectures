package com.example.springresponseentitytest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("home")
    public String home() {
        System.out.println("basic home");
        return "basic/home";
    }

    // RequestResponseBodyMethodProcessor
    @GetMapping("response-body")
    @ResponseBody
    public String responseBody() {
        return "basic-response-body";
    }

    // HttpEntityMethodProcessor
    @GetMapping("response-entity")
    public ResponseEntity<?> responseEntity() {
        return new ResponseEntity<>("basic-response-entity", HttpStatus.OK);
    }
}
