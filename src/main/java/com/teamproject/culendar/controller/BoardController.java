package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.enumFiles.RatingType;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.BoardService;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final ProgramService programService;


    // 게시글 전체 목록 (커뮤니티)
    @GetMapping("/list")
    public String list() {  // Model model, PageRequestDTO pageRequestDTO
//    log.info("**** BoardController GET /boards/list");
//    Page<Board> result = boardService.getListWithPaging(pageRequestDTO);  // 페이징
//    List<Board> contents = result.getContent();  // Board 주소(정보)들
//    List<BoardDTO> list = new ArrayList<>();
//    for(int i = 0; i < contents.size(); i++) {
//      Board board = contents.get(i);  // Board 주소(정보) 1개
//      BoardDTO dto = new BoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
//      list.add(dto);  // 리스트에 추가
//    }
//    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());
//
//    model.addAttribute("list", list); // BoardDTO 정보들 -> 글 목록 view에 전달
//    model.addAttribute("pageDTO", pageResponseDTO);

        return "community/communityHome";
    }

    // 게시글 작성
    @GetMapping("/add")
    public String addForm(@ModelAttribute("boardForm") BoardForm boardForm) {
        log.info("***** BoardController GET /boards/add");
        // TODO 모델 추가

        return "community/boardAdd";
    }
    @PostMapping("/add")
    public String addPRo(BoardForm boardForm, @AuthenticationPrincipal CustomMember customMember) {
        log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);
        ProgramDTO programDTO = programService.findById(boardForm.getProgramId());
        boardForm.setProgram(programDTO.toEntity());
        MemberDTO member = customMember.getMember();
        boardForm.setMember(member.toEntity());

        log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);

        Long save = boardService.save(boardForm);
        // TODO 게시글 작성시 완료 알림 띄우기
        return "redirect:/boards/list";
    }

    // 게시글 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomMember customMember) {
        log.info("***** BoardController GET /boards/detail - id : {}", id);
        BoardDTO board = boardService.getOneBoard(id);

        log.info("***** BoardController GET /boards/detail - board : {}", board);
        model.addAttribute("board", board);

        log.info("***** BoardController GET /boards/detail - customMember : {}", customMember);
        if (customMember != null) {
            model.addAttribute("member", customMember.getMember());
          log.info("***** BoardController GET /boards/detail - member : {}", customMember.getMember());
        }
        else {
            model.addAttribute("member", null);
        }

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

    // programType 보내기
    @ModelAttribute("boardTypes")
    public BoardType[] boardType() {
        return BoardType.values();
    }

    @ModelAttribute("ratingType") // 작품 종류 데이터를 뷰에 전달
    public RatingType[] ratingTypes() {
        List<RatingType> ratingTypes = new ArrayList<>();
        return RatingType.values();
    }


}



















