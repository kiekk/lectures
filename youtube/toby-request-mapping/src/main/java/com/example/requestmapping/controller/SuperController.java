package com.example.requestmapping.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/super")
public class SuperController {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
