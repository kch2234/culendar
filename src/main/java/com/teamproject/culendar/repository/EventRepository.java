package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.EventMemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<EventMemberList, Long> {

  @Query("SELECT eml FROM EventMemberList eml WHERE eml.member.id = :memberId AND eml.eventBoard.id = :eventBoardId")
  EventMemberList findEventMemberListByMemberIdAndEventBoardId(Long memberId, Long eventBoardId);


}
