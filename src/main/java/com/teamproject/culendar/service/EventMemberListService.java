package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.repository.EventMemberListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventMemberListService {

  private final EventMemberListRepository eventMemberListRepository;

  // EventBoard Id로 멤버 조회
  public List<MemberDTO> findMembersByEventId(Long eventId) {
    List<EventMemberList> eventMemberLists = eventMemberListRepository.findByEventBoardId(eventId);
    return eventMemberLists.stream()
        .map(eventMemberList -> new MemberDTO(eventMemberList.getMember()))
        .collect(Collectors.toList());
  }

  // Member Id로 이벤트 보드 조회
  public List<EventBoardDTO> findEventBoardsByMemberId(Long memberId) {
    List<EventMemberList> eventMemberLists = eventMemberListRepository.findByMemberId(memberId);
    return eventMemberLists.stream()
        .map(eventMemberList -> new EventBoardDTO(eventMemberList.getEventBoard()))
        .collect(Collectors.toList());
  }

}
