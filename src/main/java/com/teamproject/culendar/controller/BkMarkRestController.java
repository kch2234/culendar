package com.teamproject.culendar.controller;

import com.teamproject.culendar.service.BkMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bkmark")
public class BkMarkRestController {

    private final BkMarkService bkMarkService;

    @GetMapping("/checkProgramBkmark/{memberId}/{programId}")
    public boolean checkProgramBkmark(@PathVariable("memberId") Long memberId, @PathVariable("programId") Long programId) {
        boolean checkProgramBkmark = bkMarkService.checkProgramBkmark(memberId, programId);
        log.info("checkProgramBkmark: {}", checkProgramBkmark);
        return checkProgramBkmark;
    }

    @GetMapping("/checkBoardBkmark/{memberId}/{boardId}")
    public boolean checkBoardBkmark(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId) {
        boolean checkBoardBkmark = bkMarkService.checkBoardBkmark(memberId, boardId);
        log.info("checkBoardBkmark: {}", checkBoardBkmark);
        return checkBoardBkmark;
    }
}
