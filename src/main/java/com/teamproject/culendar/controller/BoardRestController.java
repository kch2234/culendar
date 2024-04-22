package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/findReview/{memberId}/{programId}")
    public ResponseEntity<Boolean> findProgramReviewByMemberId(@PathVariable("memberId") Long memberId, @PathVariable("programId") Long programId){

        Boolean checkResult = boardService.findProgramReviewByMemberId(memberId, programId);

        return checkResult ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
    }

}
