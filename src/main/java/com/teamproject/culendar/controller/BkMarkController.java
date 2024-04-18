package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.BoardBkMarkDTO;
import com.teamproject.culendar.dto.ProgramBkMarkDTO;
import com.teamproject.culendar.service.BkMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/bkmark")
@RequiredArgsConstructor
public class BkMarkController {

    private final BkMarkService bkMarkService;

    // 작품 북마크 추가
    @PostMapping("addProgramBkmark")
    public ResponseEntity<String> addProgramBkMark(@RequestBody ProgramBkMarkDTO programBkMarkDTO) {
        log.info("** programBkMarkDTO: {}", programBkMarkDTO);

        boolean result = bkMarkService.addProgramBkmark(programBkMarkDTO);

        if (!result) {

            bkMarkService.deleteProgramBkmark(programBkMarkDTO);
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 게시글 북마크 추가
    @PostMapping("addBoardBkmark")
    public ResponseEntity<String> addBoardBkMark(@RequestBody BoardBkMarkDTO boardBkMarkDTO) {
        log.info("** boardBkMarkDTO: {}", boardBkMarkDTO);


        boolean result = bkMarkService.addBoardBkmark(boardBkMarkDTO);

        if (!result) {

            bkMarkService.deleteBoardBkmark(boardBkMarkDTO);
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
