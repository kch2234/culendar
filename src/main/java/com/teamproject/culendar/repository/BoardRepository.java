package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
  Page<Board> findByTitle(String title, Pageable pageable); // count 쿼리 사용
  List<Board> findByMember(String member, Pageable pageable);


}
