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
public class EventController {

  private final EventBoardService eventBoardService;

  // 모임 목록 (클럽)
  @GetMapping("/list")
  public String list(Model model, PageRequestDTO pageRequestDTO) {
    log.info("**** EventController GET /clubs/list");
    Page<EventBoard> result = eventBoardService.getListWithPaging(pageRequestDTO);
    List<EventBoard> contents = result.getContent();
    List<EventDTO> list = new ArrayList<>();
    for(int i = 0; i < contents.size(); i++) {
      EventBoard board = contents.get(i);
      EventDTO dto = new EventDTO(board);
      list.add(dto);
    }
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());

    model.addAttribute("list", list);
    model.addAttribute("pageDTO", pageResponseDTO);

    return "club/clubHome";
  }

  // 모임 작성
  @GetMapping("/add")
  public String addForm(@ModelAttribute EventBoardForm eventBoardForm) {
    log.info("***** EventController GET /clubs/add");

    return "club/eventAdd";
  }
  @PostMapping("/add")
  public String addPRo(EventBoardForm eventBoardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("**** EventController POST /clubs/add - boardForm : {}", eventBoardForm);
    eventBoardForm.setWriter(customMember.getUsername());
    Long saveId = eventBoardService.save(eventBoardForm);

    return "redirect:/club/clubHome";
  }

  // 모임 상세
  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    log.info("***** EventController GET /club/eventDetail - bid : {}", id);
    EventBoardDTO board = EventboardService.getOneBoard(id);
    log.info("***** EventController GET /clubs/detail - event : {}", board);
    model.addAttribute("board", board);

    return "club/eventDetail";

  }

  // 모임 삭제
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id, String writer) {
    log.info("**** EventController POST /clubs/:id/delete - id : {}", id);
    EventBoardService.deleteOneBoard(id);
    return "redirect:clubs/list";
  }

  // 모임 수정
  @GetMapping("/{id}/modify")
  public String modifyForm(@PathVariable("id") Long id, Model model) {
    log.info("**** EventController GET /clubs/:id/modify - id : {}", id);
    EventBoardDTO board = EventBoardService.getOneBoard(id);
    model.addAttribute("board", board);
    return "club/eventModify";
  }
  @PostMapping("/{id}/modify")
  public String modifyPro(@PathVariable("id") Long id, BoardForm boardForm) {
    log.info("**** EventController GET /clubs/:id/modify - id : {}", id);
    log.info("**** EventController GET /clubs/:id/modify - eventBoardForm : {}", eventBoardForm);
    eventboardService.updateOneBoard(boardForm);
    return "redirect:/clubs/{id}";
  }



}



















