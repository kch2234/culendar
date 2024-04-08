package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.*;
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
@RequestMapping("/boards")
public class BoardController {

  private final BoardService boardService;

  // 게시글 목록 (커뮤니티)
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

    model.addAttribute("list", list); // 글 목록 view에 전달
    model.addAttribute("pageDTO", pageResponseDTO);

    return "community/communityHome";
  }

  // 게시글 작성
  @GetMapping("/add")
  public String addForm(@ModelAttribute BoardForm boardForm, Model model, @AuthenticationPrincipal CustomMember customMember) {
    log.info("***** BoardController GET /boards/add");
    model.addAttribute("boardType", BoardType.values());

    return "community/boardAdd";
  }
  @PostMapping("/add")
  public String addPRo(BoardForm boardForm, @AuthenticationPrincipal CustomMember customMember) {
    log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);
    MemberDTO member = customMember.getMember();
    log.info("***** BoardController POST /boards/add - writer(member.username) : {}", member.getUsername());
    boardForm.setMember(member.toEntity());
    Long save = boardService.save(boardForm);

    return "redirect:/boards/list";
  }

  // 게시글 상세
  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    log.info("***** BoardController GET /boards/detail - id : {}", id);
    BoardDTO board = boardService.getOneBoard(id);
    log.info("***** BoardController GET /boards/detail - board : {}", board);
    model.addAttribute("board", board);

    return "community/boardDetail";

  }

  // 게시글 삭제
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id) {
    log.info("**** BoardController POST /boards/:id/delete - id : {}", id);
    boardService.deleteOneBoard(id);
    return "redirect:/boards/list";
  }

  // 게시글 수정
  @GetMapping("/{id}/modify")
  public String modifyForm(@PathVariable("id") Long id, Model model) {
    log.info("**** BoardController GET /boards/:id/modify - id : {}", id);
    BoardDTO board = boardService.getOneBoard(id);
    model.addAttribute("board", board);
    return "community/boardModify";
  }
  @PostMapping("/{id}/modify")
  public String modifyPro(@PathVariable("id") Long id, BoardForm boardForm) {
    log.info("**** BoardController GET /boards/:id/modify - id : {}", id);
    log.info("**** BoardController GET /boards/:id/modify - boardForm : {}", boardForm);
    boardService.updateOneBoard(boardForm);
    return "redirect:/boards/{id}";
  }



}



















