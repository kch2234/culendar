package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {

  Page<EventBoard> findByTitle(String title, Pageable pageable);
  List<EventBoard> findByMember(String member, Pageable pageable);

  @Query("SELECT eb FROM EventBoard eb JOIN eb.program p WHERE p.programType = :programType")
  Page<EventBoard> findByProgramType(ProgramType programType, Pageable pageable);

}
