package com.example.studyluxyxssfilter.controller.web.board;

import com.example.studyluxyxssfilter.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = {"", "/"})
    public String index() {
        return "redirect:/board/list";
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("list", boardService.findAll());
        return "board/list";
    }

    @GetMapping("register")
    public String register() {
        return "board/register";
    }
}
