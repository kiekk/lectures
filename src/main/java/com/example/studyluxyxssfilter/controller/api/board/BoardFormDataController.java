package com.example.studyluxyxssfilter.controller.api.board;

import com.example.studyluxyxssfilter.entity.Board;
import com.example.studyluxyxssfilter.service.BoardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/form-data")
public class BoardFormDataController {

    private final BoardService boardService;

    public BoardFormDataController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("")
    public void insert(Board board) {
        boardService.save(board);
    }

    @PutMapping("{id}")
    public void update(@PathVariable String id, Board board) {
        board.setId(id);
        boardService.update(board);
    }

}
