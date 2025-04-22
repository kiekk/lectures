package com.inflearn.security.config.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping(value = "/admin/home")
    public String home() {
        return "admin/home";
    }
}
