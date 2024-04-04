package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventBoardRepository extends JpaRepository<Board, Long> {

  Page<EventBoard> findByTitle(String title, Pageable pageable);
  List<EventBoard> findByWriter(String writer, Pageable pageable);


}
