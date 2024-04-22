package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.BoardBkMarkDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.repository.BoardRepository;
import com.teamproject.culendar.repository.EventBoardRepository;
import com.teamproject.culendar.repository.EventRepository;
import com.teamproject.culendar.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;
  private final EventBoardRepository eventBoardRepository;
  private final MemberRepository memberRepository;

  // 모임 신청
  @Transactional
  public boolean applyEventMember(EventMemberList eventMemberList) {
    log.info("***** EventService applyEventMember - eventMemberList : {}", eventMemberList);

    /*// 중복 체크
    long memberId = eventMemberList.getMember().getId();
    long eventBoardId = eventMemberList.getEventBoard().getId();
    log.info("****** EventService applyEventMember - memberId : {}, boardIdVal: {}", memberId, eventBoardId);
    EventMemberList findEventMemberList = eventRepository.findEventMemberListByMemberIdAndEventBoardId(memberId, eventBoardId);
    log.info("****** EventService applyEventMember : {}", findEventMemberList);

    if (findEventMemberList != null) {
      log.info("** 이미 신청한 모임입니다.");
      return false;
    }*/

    EventBoard eventBoard = eventBoardRepository.findById(eventMemberList.getEventBoard().getId()).orElse(null);
    Member member = memberRepository.findById(eventMemberList.getMember().getId()).orElse(null);

    eventMemberList.setEventBoard(eventBoard);
    eventMemberList.setMember(member);

    eventRepository.save(eventMemberList);

    return true;
  }

  // 모임 신청 취소
  public void cancelEventMember(EventMemberList eventMemberList) {
    log.info("***** EventService cancelEventMember - eventMemberList: {}", eventMemberList);
    EventMemberList findEventMemberList = eventRepository.findEventMemberListByMemberIdAndEventBoardId(eventMemberList.getMember()
        .getId(), eventMemberList.getEventBoard().getId());

    eventRepository.delete(findEventMemberList);
  }

}
