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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String list() {

        return "community/communityHome";
    }

    // 게시글 작성
    @GetMapping("/add")
    public String addForm(@ModelAttribute("boardForm") BoardForm boardForm) {
        log.info("***** BoardController GET /boards/add");

        return "community/boardAdd";
    }
    @PostMapping("/add")
    public String addPRo(BoardForm boardForm, @AuthenticationPrincipal CustomMember customMember, RedirectAttributes rttr) {
        log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);
        ProgramDTO programDTO = programService.findById(boardForm.getProgramId());
        boardForm.setProgram(programDTO.toEntity());
        MemberDTO member = customMember.getMember();
        boardForm.setMember(member.toEntity());

        log.info("**** BoardController POST /boards/add - boardForm : {}", boardForm);

        Long save = boardService.save(boardForm);

        if (save != null) {
            rttr.addFlashAttribute("result", "success");
        }
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
        log.info("게시글 삭제");
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

    @GetMapping("/addReview/{programId}")
    public String addReview(RedirectAttributes rttr, @PathVariable("programId") Long programId) {
        log.info("***** BoardController GET /boards/addReview");

        rttr.addFlashAttribute("reviewProgramId", programId);
        rttr.addFlashAttribute("fromReviewBtn",true);

        return "redirect:/boards/add";
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



















