package com.example.springbootjartest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"", "/"})
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("home")
    public String home() {
        return "home";
    }

    @RequestMapping("test")
    public String test() {
        return "test";
    }

}
