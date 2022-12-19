package com.example.studyluxyxssfilter.controller.api.board.serveltfilter;

import com.example.studyluxyxssfilter.controller.api.board.BoardFormDataController;
import com.example.studyluxyxssfilter.service.BoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lucy-xss-servlet-filter/api/board/form-data")
public class LucyBoardFormDataController extends BoardFormDataController {
    public LucyBoardFormDataController(BoardService boardService) {
        super(boardService);
    }
}
