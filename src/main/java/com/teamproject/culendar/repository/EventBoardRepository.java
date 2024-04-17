package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {

  Page<EventBoard> findByTitle(String title, Pageable pageable);
  List<EventBoard> findByMember(String member, Pageable pageable);

  @Query("SELECT eb FROM EventBoard eb JOIN eb.program p WHERE p.programType = :programType")
  Page<EventBoard> findByProgramType(@Param("programType") ProgramType programType, Pageable pageable);

  @Query("SELECT b " +
      "FROM EventBoard b " +
      "LEFT JOIN EventBoardBkmark bb ON b.id = bb.eventBoard.id " +
      "GROUP BY b " +
      "ORDER BY COUNT(bb) DESC")
  Page<EventBoard> findOrderByBkMark(Pageable pageable);
}
