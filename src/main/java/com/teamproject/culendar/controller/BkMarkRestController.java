package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.BoardBkMarkDTO;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.service.BkMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    // 북마크 작품 조회
//    @GetMapping("/findProgramBkmark/{memberId}/{programId}")
//    public ResponseEntity<ProgramDTO> findProgramBkmarkByMemberId(@PathVariable("memberId") Long memberId, @PathVariable("programId") Long programId){
//
//        ProgramDTO programBkmarkByMemberId = bkMarkService.findProgramBkmarkByMemberId(memberId, programId);
//
//        return ResponseEntity.ok().body(programBkmarkByMemberId);
//
//    }

    // 북마크 작품 최신순으로 무한 스크롤 조회
    @GetMapping("/findMyProgramWithPaging/{memberId}/{page}/{size}")
    public ResponseEntity<Page<ProgramDTO>> findMyProgramWithPaging(
        @PathVariable("memberId") Long memberId,
        @PathVariable("page") int page,
        @PathVariable("size") int size) {

        Page<ProgramDTO> programDTOPage = bkMarkService.findProgramBkmarkByMemberIdWithPaging(memberId, page, size);

        return ResponseEntity.ok(programDTOPage);
    }






}
