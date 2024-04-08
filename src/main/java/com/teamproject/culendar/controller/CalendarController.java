package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.CalendarDTO;
import com.teamproject.culendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calendar")
@Slf4j
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    /*@GetMapping("list")
    public ResponseEntity<CalendarDTO> list() {
        log.info("** CalendarController GET /list 요청");
        CalendarDTO calendarDTO = calendarService.getProgramList();
        return ResponseEntity.ok(calendarDTO);
    }*/
    @GetMapping("list")
    public ResponseEntity<List<CalendarDTO>> list(@RequestParam("programType") String programType, @RequestParam("location") String location) {
        log.info("** CalendarController GET /list 요청");
        log.info("** programType: {}", programType);
        log.info("** location: {}", location);
        List<Program> programList = calendarService.getProgramList(programType, location);
        List<CalendarDTO> calendarDTOList = programList.stream()
                .map(CalendarDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(calendarDTOList);
    }
}
