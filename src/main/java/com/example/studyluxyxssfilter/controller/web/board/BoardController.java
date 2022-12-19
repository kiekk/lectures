package com.example.studyluxyxssfilter.controller.web.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping(value = {"", "/"})
    public String index() {
        return "redirect:/board/list";
    }


    @GetMapping("list")
    public String list() {
        return "/board/list";
    }

    @GetMapping("register")
    public String register() {
        return "/board/register";
    }
}
