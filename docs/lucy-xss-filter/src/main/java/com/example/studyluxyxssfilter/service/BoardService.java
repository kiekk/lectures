package com.example.studyluxyxssfilter.service;

import com.example.studyluxyxssfilter.entity.Board;
import com.example.studyluxyxssfilter.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findOne(String id) {
        return boardRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void save(Board board) {
        board.setId(UUID.randomUUID().toString());
        boardRepository.save(board);
    }

    public void update(Board board) {
        Board fetch = findOne(board.getId());
        fetch.updateFields(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

}
