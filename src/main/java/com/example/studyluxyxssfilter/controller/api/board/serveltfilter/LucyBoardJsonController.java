package com.example.studyluxyxssfilter.controller.api.board.serveltfilter;

import com.example.studyluxyxssfilter.controller.api.board.BoardJsonController;
import com.example.studyluxyxssfilter.service.BoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lucy-xss-servlet-filter/api/board/json")
public class LucyBoardJsonController extends BoardJsonController {
    public LucyBoardJsonController(BoardService boardService) {
        super(boardService);
    }
}
