package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardBkMarkRepository extends JpaRepository<BoardBkmark, Long> {

    BoardBkmark findBoardBkmarkByMemberIdAndBoardId(Long memberId, Long boardId);

    }
