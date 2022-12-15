package com.example.studyluxyxssfilter.repository;

import com.example.studyluxyxssfilter.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
}
