package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
  Page<Board> findByTitle(String title, Pageable pageable); // count 쿼리 사용
  List<Board> findByMember(String member, Pageable pageable);

  Page<Board> findByBoardType(BoardType boardType, Pageable pageable);

  @Query("SELECT b " +
      "FROM Board b " +
      "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
      "GROUP BY b " +
      "ORDER BY COUNT(bb) DESC")
  Page<Board> findOrderByBkMark(Pageable pageable);

  // 최근 일주일 동안 최고 인기글 4개 조회
    @Query("SELECT b " +
        "FROM Board b " +
        "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
        "WHERE b.createDate >= CURRENT_DATE - 7 " +
        "GROUP BY b " +
        "ORDER BY COUNT(bb) DESC")
    List<Board> findBestByBkMark();
}
