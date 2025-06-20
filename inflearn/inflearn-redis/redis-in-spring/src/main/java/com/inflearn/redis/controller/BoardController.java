package com.inflearn.redis.controller;

import com.inflearn.redis.entity.Board;
import com.inflearn.redis.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("redis")
    public List<Board> getBoardsRedis(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size
    ) {
        return boardService.getBoardsRedis(page, size);
    }

    @GetMapping("no-redis")
    public List<Board> getBoardsNoRedis(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size
    ) {
        return boardService.getBoardsNoRedis(page, size);
    }

}
