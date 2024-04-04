package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.BoardForm;
import com.teamproject.culendar.dto.PageRequestDTO;
import com.teamproject.culendar.dto.PageResponseDTO;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.BoardService;
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

  private final BoardService boardService;

  // 모임 목록 (클럽)
  @GetMapping("/list")
  public String list(Model model, PageRequestDTO pageRequestDTO) {
    log.info("**** BoardController GET /boards/list");
    Page<Board> result = boardService.getListWithPaging(pageRequestDTO);
    List<Board> contents = result.getContent();
    List<BoardDTO> list = new ArrayList<>();
    for(int i = 0; i < contents.size(); i++) {
      Board board = contents.get(i);
      BoardDTO dto = new BoardDTO(board);
      list.add(dto);
    }
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());

    model.addAttribute("list", list);
    model.addAttribute("pageDTO", pageResponseDTO);

    return "club/clubHome";
  }

  // 모임 작성
  @GetMapping("/add")
  public String addForm(@ModelAttribute BoardForm boardForm) {
    log.info("***** BoardController GET /boards/add");

    return "club/eventAdd";
  }
  @PostMapping("/add")
  public String addPRo(BoardForm boardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);
    boardForm.setWriter(customMember.getUsername());
    Long saveId = boardService.save(boardForm);

    return "redirect:/club/clubHome";
  }

  // 모임 상세
  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    log.info("***** BoardController GET /community/boardDetail - bid : {}", id);
    BoardDTO board = boardService.getOneBoard(id);
    log.info("***** BoardController GET /community/boardDetail - board : {}", board);
    model.addAttribute("board", board);

    return "club/eventDetail";

  }

  // 모임 삭제
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id, String writer) {
    log.info("**** BoardController POST /boards/:id/delete - id : {}", id);
    boardService.deleteOneBoard(id);
    return "redirect:clubs/list";
  }

  // 모임 수정
  @GetMapping("/{id}/modify")
  public String modifyForm(@PathVariable("id") Long id, Model model) {
    log.info("**** BoardController GET /boards/:id/modify - id : {}", id);
    BoardDTO board = boardService.getOneBoard(id);
    model.addAttribute("board", board);
    return "club/eventModify";
  }
  @PostMapping("/{id}/modify")
  public String modifyPro(@PathVariable("id") Long id, BoardForm boardForm) {
    log.info("**** BoardController GET /boards/:id/modify - id : {}", id);
    log.info("**** BoardController GET /boards/:id/modify - boardForm : {}", boardForm);
    boardService.updateOneBoard(boardForm);
    return "redirect:/clubs/{id}";
  }



}



















