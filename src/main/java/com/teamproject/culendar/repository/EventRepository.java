package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventMemberList, Long> {

  @Query("SELECT eml FROM EventMemberList eml WHERE eml.member.id = :memberId AND eml.eventBoard.id = :eventBoardId")
  EventMemberList findEventMemberListByMemberIdAndEventBoardId(@Param("memberId") Long memberId, @Param("eventBoardId") Long eventBoardId);

  // viewCount가 높은 순으로 4개의 이벤트 리스트를 가져옴
  @Query("SELECT eb FROM EventBoard eb ORDER BY eb.viewCount DESC")
  List<EventBoard> findBestEventList();
}
