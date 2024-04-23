package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
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

@RestController @Slf4j @RequestMapping("/event") @RequiredArgsConstructor public class EventController {

  private final EventService eventService;
  private final MemberService memberService;
  private final EventBoardService eventBoardService;

  @PostMapping("/applyEventMember")
  public ResponseEntity<String> applyEventMember(@RequestBody Map<String, String> requestData) {
    String applyStatus = requestData.get("applyStatus");
    Long memberId = Long.parseLong(requestData.get("memberId"));
    Long eventBoardId = Long.parseLong(requestData.get("eventBoardId"));
    log.info("***** EventController /event/applyEventMember - memberId: {}", memberId);
    log.info("***** EventController /event/applyEventMember - eventBoardId: {}", eventBoardId);
    log.info("***** EventController /event/applyEventMember - applyStatus: {}", applyStatus);

    EventMemberList eventMemberList = new EventMemberList();
    Member member = memberService.findById(memberId).toEntity();
    eventMemberList.setMember(member);

    EventBoardDTO eventBoardDTO = eventBoardService.getOneBoard(eventBoardId);
    EventBoard eventBoard = new EventBoard();
    eventBoard.setId(eventBoardDTO.getId());
    eventMemberList.setEventBoard(eventBoard);

    boolean result = eventService.applyEventMember(eventMemberList);

    if (!result) {
      log.info("***** EventController /event/applyEventMember - result = fail");
      return new ResponseEntity<>("fail", HttpStatus.OK);
    }
    log.info("***** EventController /event/applyEventMember - result = success");
    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}

