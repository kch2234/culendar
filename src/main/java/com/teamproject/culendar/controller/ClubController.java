package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.EventBoardService;
import com.teamproject.culendar.service.EventMemberListService;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {  // *ClubController == EventBoardController* 모임 페이지 관련 컨트롤러

  private final EventBoardService eventBoardService;
  private final EventMemberListService eventMemberListService;
  private final ProgramService programService;

  // 모임 목록 (클럽)
  @GetMapping("/list")
  public String list(Model model, ProgramType programType) {

    model.addAttribute("programType", ProgramType.values());

    return "club/clubHome";
  }

  // 모임 작성
  @GetMapping("/add")
  public String addForm(@ModelAttribute("eventBoardForm") EventBoardForm eventBoardForm, Model model) {  // @AuthenticationPrincipal CustomMember customMember
    log.info("***** ClubController GET /clubs/add");
//    model.addAttribute("location", Location.values());
    model.addAttribute("filterGender", Gender.values());

    return "club/eventAdd";
  }
  @PostMapping("/add")
  public String addPRo(EventBoardForm eventBoardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("**** ClubController POST /clubs/add - eventBoardForm : {}", eventBoardForm);

    MemberDTO member = customMember.getMember();
    eventBoardForm.setMember(member.toEntity());

    log.info("**** ClubController POST /clubs/add - member : {}", member);
    ProgramDTO programDTO = programService.findById(eventBoardForm.getProgramId());
    eventBoardForm.setProgram(programDTO.toEntity());

    log.info("**** ClubController POST /clubs/add - eventBoardForm : {}", eventBoardForm);
    Long save = eventBoardService.save(eventBoardForm);

    return "redirect:/clubs/list";
  }

  // 모임 상세
  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomMember customMember) {
    log.info("***** ClubController GET /community/boardDetail - id : {}", id);

    EventBoardDTO eventBoard = eventBoardService.getOneBoard(id);
    model.addAttribute("eventBoard", eventBoard);
    List<MemberDTO> eventMembers = eventMemberListService.findMembersByEventId(id);
    model.addAttribute("eventMembers", eventMembers);

    if (customMember != null) {
      model.addAttribute("member", customMember.getMember());
      log.info("***** BoardController GET /boards/detail - member : {}", customMember.getMember());
    }
    else {
      model.addAttribute("member", null);
    }

    return "club/eventDetail";

  }

  // 모임 삭제
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id) {
    log.info("**** ClubController POST /clubs/:id/delete - id : {}", id);
    eventBoardService.deleteOneBoard(id);
    return "redirect:/clubs/list";
  }

  // 모임 수정
  @GetMapping("/{id}/modify")
  public String modifyForm(@PathVariable("id") Long id, Model model) {
    log.info("**** ClubController GET /clubs/:id/modify - id : {}", id);
    EventBoardDTO eventBoard = eventBoardService.getOneBoard(id);

    String eventDateString = eventBoard.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String deadlineDateString = eventBoard.getDeadlineDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    model.addAttribute("eventDateString", eventDateString);
    model.addAttribute("deadlineDateString", deadlineDateString);

    model.addAttribute("filterGender", Gender.values());

    log.info("***** ClubController GET /clubs/:id/modify - eventBoardDTO : {}", eventBoard);
    model.addAttribute("eventBoard", eventBoard);

    return "club/eventModify";
  }
  @PostMapping("/{id}/modify")
  public String modifyPro(@PathVariable("id") Long id, EventBoardForm eventBoardForm) {
    log.info("**** ClubController POST /clubs/:id/modify - id : {}", id);
    log.info("**** ClubController POST /clubs/:id/modify - eventBoardForm : {}", eventBoardForm);

    eventBoardService.updateOneBoard(eventBoardForm);
    return "redirect:/clubs/{id}";
  }



}



















