package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventMemberListRepository extends JpaRepository<EventMemberList, Long> {

  // eventBoard.id를 사용하여 멤버 조회
  List<EventMemberList> findByEventBoardId(Long eventId);

  // member.id를 사용하여 이벤트 보드 조회
  List<EventMemberList> findByMemberId(Long memberId);
}
