package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.EventBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

  private final EventBoardService eventBoardService;

  // 모임 목록 (클럽)
  @GetMapping("/list")
  public String list(Model model, PageRequestDTO pageRequestDTO) {
    log.info("**** ClubController GET /boards/list");
    Page<EventBoard> result = eventBoardService.getListWithPaging(pageRequestDTO);
    List<EventBoard> contents = result.getContent();
    List<EventBoardDTO> list = new ArrayList<>();
    for(int i = 0; i < contents.size(); i++) {
      EventBoard eventBoard = contents.get(i);
      EventBoardDTO dto = new EventBoardDTO(eventBoard);
      list.add(dto);
    }
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());

    model.addAttribute("list", list);
    model.addAttribute("pageDTO", pageResponseDTO);

    return "club/clubHome";
  }

  // 모임 작성
  @GetMapping("/add")
  public String addForm(@ModelAttribute BoardForm eventBoardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("***** ClubController GET /boards/add");

    return "club/eventAdd";
  }
  @PostMapping("/add")
  public String addPRo(EventBoardForm eventBoardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("**** ClubController POST /boards/add - eventBoardForm : {}", eventBoardForm);
    MemberDTO member = customMember.getMember();
    eventBoardForm.setMember(member.toEntity());
    Long save = eventBoardService.save(eventBoardForm);

    return "redirect:/club/clubHome";
  }

  // 모임 상세
  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    log.info("***** ClubController GET /community/boardDetail - bid : {}", id);
    EventBoardDTO eventBoard = eventBoardService.getOneBoard(id);
    log.info("***** ClubController GET /community/boardDetail - eventBoard : {}", eventBoard);
    model.addAttribute("eventBoard", eventBoard);

    return "club/eventDetail";

  }

  // 모임 삭제
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id, String writer) {
    log.info("**** ClubController POST /boards/:id/delete - id : {}", id);
    eventBoardService.deleteOneBoard(id);
    return "redirect:clubs/list";
  }

  // 모임 수정
  @GetMapping("/{id}/modify")
  public String modifyForm(@PathVariable("id") Long id, Model model) {
    log.info("**** ClubController GET /boards/:id/modify - id : {}", id);
    EventBoardDTO eventBoard = eventBoardService.getOneBoard(id);
    model.addAttribute("eventBoard", eventBoard);
    return "club/eventModify";
  }
  @PostMapping("/{id}/modify")
  public String modifyPro(@PathVariable("id") Long id, EventBoardForm eventBoardForm) {
    log.info("**** ClubController GET /boards/:id/modify - id : {}", id);
    log.info("**** ClubController GET /boards/:id/modify - eventBoardForm : {}", eventBoardForm);
    eventBoardService.updateOneBoard(eventBoardForm);
    return "redirect:/clubs/{id}";
  }



}



















