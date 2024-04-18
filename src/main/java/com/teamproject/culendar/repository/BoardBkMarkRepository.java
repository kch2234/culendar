package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.BoardBkmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardBkMarkRepository extends JpaRepository<BoardBkmark, Long> {

    BoardBkmark findBoardBkmarkByMemberIdAndBoardId(Long memberId, Long boardId);
}
