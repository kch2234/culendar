package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.EventMemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<EventMemberList, Long> {

  @Query("SELECT eml FROM EventMemberList eml WHERE eml.member.id = :memberId AND eml.eventBoard.id = :eventBoardId")
  EventMemberList findEventMemberListByMemberIdAndEventBoardId(@Param("memberId") Long memberId, @Param("eventBoardId") Long eventBoardId);


}
