package com.example.studyluxyxssfilter.controller.api.board;

import com.example.studyluxyxssfilter.entity.Board;
import com.example.studyluxyxssfilter.service.BoardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/json")
public class BoardJsonController {

    private final BoardService boardService;

    public BoardJsonController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("")
    public void insert(@RequestBody Board board) {
        boardService.save(board);
    }

    @PutMapping("{id}")
    public void update(@PathVariable String id,
                       @RequestBody Board board) {
        board.setId(id);
        boardService.update(board);
    }

}