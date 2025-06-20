package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.service.ProgramService;
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
@RequestMapping("/program")
public class ProgramRestController {

    private final ProgramService programService;


    @GetMapping("search/{keyword}")
    public ResponseEntity<List<ProgramDTO>> searchProgram(@PathVariable("keyword") String keyword) {
        log.info("** ProgramController GET /program/search/:keyword - keyword: {}", keyword);
        List<ProgramDTO> programDTOList = programService.searchProgram(keyword);
        log.info("** ProgramController GET /program/search/:keyword - programDTOList: {}", programDTOList);

        return ResponseEntity.ok(programDTOList);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ProgramDTO> findProgram(@PathVariable("id") Long id) {
        ProgramDTO programDTO = programService.findById(id);

        return ResponseEntity.ok(programDTO);
    }

    @GetMapping("findBySeq/{seq}")
    public ResponseEntity<ProgramDTO> findProgramBySeq(@PathVariable("seq") Long seq) {
        ProgramDTO programDTO = programService.getOneProgram(seq);

        return ResponseEntity.ok(programDTO);
    }

    @GetMapping("bestProgramList")
    public ResponseEntity<List<ProgramDTO>> bestProgramList() {
        log.info("** ProgramController GET /program/bestProgramList");
        List<ProgramDTO> programDTOList = programService.bestProgramList();
        // 만약 리스트가 비어있으면 null 반환
        if (programDTOList.size() == 0) {
            return ResponseEntity.ok(null);
        }
        // 리스트 첫번째 꺼내기
        ProgramDTO programDTO = programDTOList.get(0);
        log.info("** ProgramController GET /program/bestProgramList - programDTO: {}", programDTO.getSeq());

        return ResponseEntity.ok(programDTOList);
    }


}
