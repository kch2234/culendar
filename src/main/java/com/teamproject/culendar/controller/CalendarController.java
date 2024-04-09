package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.CalendarDTO;
import com.teamproject.culendar.dto.CalendarNameDTO;
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

    @GetMapping("myCalendarList")
    public ResponseEntity<List<CalendarDTO>> myCalendarList(@RequestParam("programType") String programType, @RequestParam("location") String location, @RequestParam("userid") Long userid, @RequestParam("calendarId") Long calendarId) {
        log.info("** CalendarController GET /myCalendarList 요청");
        log.info("** programType: {}", programType);
        log.info("** location: {}", location);
        log.info("** memberId: {}", userid);
        log.info("** calendarName: {}", calendarId);
        return null;
    }

    @GetMapping("myCalendarNameList/{id}")
    public ResponseEntity<List<CalendarNameDTO>> myCalendarNameList(@PathVariable("id") Long userid) {
        log.info("** CalendarController GET /myCalendarNameList 요청");
        log.info("** memberId: {}", userid);

        List<CalendarNameDTO> calendarNameDTOList = calendarService.getCalendarList(userid);
        // 리스트 안의 member 객체를 null로 만들어줌
        calendarNameDTOList.forEach(calendarNameDTO -> calendarNameDTO.setMember(null));
        log.info("** CalendarController GET /myCalendarNameList - calendarNameDTOList : {}", calendarNameDTOList);

        return ResponseEntity.ok(calendarNameDTOList);

    }

    @GetMapping("saveCalendarContent/{memberid}/{calendarid}/{programid}")
    public ResponseEntity<String> saveCalendarContent(@PathVariable("memberid") Long memberid, @PathVariable("calendarid") Long calendarid, @PathVariable("programid") Long programid) {
        log.info("** CalendarController GET /saveCalendarContent 요청");
        log.info("** memberId: {}", memberid);
        log.info("** calendarId: {}", calendarid);
        log.info("** programId: {}", programid);
        return null;
    }

}
