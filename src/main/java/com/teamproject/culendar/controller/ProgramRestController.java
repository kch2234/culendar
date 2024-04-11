package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/program")
public class ProgramRestController {

    private final ProgramService programService;

    @GetMapping("search/{keyword}")
    public ResponseEntity<List<ProgramDTO>> searchProgram(@PathVariable("keyword") String keyword){
        log.info("** ProgramController GET /program/search/:keyword - keyword: {}", keyword);
        List<ProgramDTO> programDTOList = programService.searchProgram(keyword);
        log.info("** ProgramController GET /program/search/:keyword - programDTOList: {}", programDTOList);

        return ResponseEntity.ok(programDTOList);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ProgramDTO> findProgram(@PathVariable("id") Long id){
        log.info("** ProgramController GET /program/find/:id - id: {}", id);
        ProgramDTO programDTO = programService.findProgram(id);
        log.info("** ProgramController GET /program/find/:id - programDTO: {}", programDTO);

        return ResponseEntity.ok(programDTO);
    }


}
