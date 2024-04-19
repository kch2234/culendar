package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.dto.SearchDTO;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final ProgramService programService;

    @GetMapping("/searchProgramList")
    public ResponseEntity<List<ProgramDTO>> searchProgramList() {
        log.info("*** SearchController GET /list 요청");
        List<ProgramDTO> ListProgramAll = programService.searchProgramList();
        return ResponseEntity.ok(ListProgramAll);
    }
}
