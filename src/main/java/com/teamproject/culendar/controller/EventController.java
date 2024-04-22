package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.service.EventBoardService;
import com.teamproject.culendar.service.EventService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final MemberService memberService;
  private final EventBoardService eventBoardService;

  // TODO 모임 신청
  @PostMapping("/applyEventMember")
  public ResponseEntity<String> applyEventMember(@RequestBody Map<String, Long> requestData) {
    Long memberId = requestData.get("memberId");
    Long eventBoardId = requestData.get("eventBoardId");
    log.info("***** EventController /event/applyEventMember - memberId: {}", memberId);
    log.info("***** EventController /event/applyEventMember - eventBoardId: {}", eventBoardId);

    EventMemberList eventMemberList = new EventMemberList();

    Member member = memberService.findById(memberId).toEntity();
    eventMemberList.setMember(member);

    EventBoardDTO eventBoardDTO = eventBoardService.getOneBoard(eventBoardId);
    // TODO EventBoardDTO -> EventBoard  변환
    EventBoard eventBoard = new EventBoard();
    eventMemberList.setEventBoard(eventBoard);

    boolean result = eventService.applyEventMember(eventMemberList);

    // 모임 신청에 실패 시 fail 리턴
    if (!result) {
      return new ResponseEntity<>("fail", HttpStatus.OK);
    }
    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}
