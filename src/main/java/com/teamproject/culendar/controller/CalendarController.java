package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.CalendarContentDTO;
import com.teamproject.culendar.dto.CalendarDTO;
import com.teamproject.culendar.dto.CalendarNameDTO;
import com.teamproject.culendar.service.CalendarService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    // programType, location을 받아서 해당하는 작품들 리스트를 반환
    @GetMapping("list")
    public ResponseEntity<List<CalendarDTO>> list(@RequestParam("programType") String programType, @RequestParam("location") String location) {
        log.info("** CalendarController GET /list 요청");
        log.info("** programType: {}", programType);
        log.info("** location: {}", location);
        // programType, location에 해당하는 작품들 리스트를 가져옴
        List<Program> programList = calendarService.getProgramList(programType, location);
        // programList를 FullCalendar event 형식에 맞는 CalendarDTO로 변환
        List<CalendarDTO> calendarDTOList = programList.stream()
                .map(CalendarDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(calendarDTOList);
    }

    // memberId, calendarId programType, location 을 받아서 해당하는 작품들 리스트를 반환
    @GetMapping("myCalendarList")
    public ResponseEntity<List<CalendarDTO>> myCalendarList(@RequestParam("programType") String programType, @RequestParam("location") String location, @RequestParam("calendarId") Long calendarId) {
        log.info("** CalendarController GET /myCalendarList 요청");
        log.info("** programType: {}", programType);
        log.info("** location: {}", location);
        log.info("** calendarName: {}", calendarId);

        // programType, location에 해당하는 작품들 리스트를 가져옴
        List<Program> programList = calendarService.getProgramFromMyCal(programType, location, calendarId);
        // programList를 FullCalendar event 형식에 맞는 CalendarDTO로 변환
        List<CalendarDTO> calendarDTOList = programList.stream()
                .map(CalendarDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(calendarDTOList);
    }

    // memberId를 받아서 회원이 가지고 있는 달력 이름 리스트를 반환
    @GetMapping("myCalendarNameList/{id}")
    public ResponseEntity<List<CalendarNameDTO>> myCalendarNameList(@PathVariable("id") Long userid) {
        log.info("** CalendarController GET /myCalendarNameList 요청");
        log.info("** memberId: {}", userid);

        List<CalendarNameDTO> calendarNameDTOList = calendarService.getCalendarList(userid);

        if (calendarNameDTOList.size() == 0) {
            calendarService.saveCalendarName(userid);
            calendarNameDTOList = calendarService.getCalendarList(userid);
            log.info("***** HomeController GET /myCalendar - myCalendarList : {}", calendarNameDTOList);
        }
        // 리스트 안의 member 객체를 null로 만들어줌
        calendarNameDTOList.forEach(calendarNameDTO -> calendarNameDTO.setMember(null));
        log.info("** CalendarController GET /myCalendarNameList - calendarNameDTOList : {}", calendarNameDTOList);

        return ResponseEntity.ok(calendarNameDTOList);

    }


    // memberId, calendarId, programId를 받아서 해당하는 작품을 달력에 저장
    @PostMapping("addProgramToCalendar")
    public ResponseEntity<String> addProgramToCalendar(@RequestBody CalendarContentDTO calendarContentDTO) {
        log.info("** CalendarController POST /addProgramToCalendar 요청");
        log.info("** calendarContentDTO: {}", calendarContentDTO);

        boolean result = calendarService.saveCalendarContent(calendarContentDTO);

        if (!result) {
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("addNewCalendar")
    public ResponseEntity<String> addNewCalendar(@RequestBody CalendarNameDTO calendarNameDTO) {
        log.info("** CalendarController POST /addNewCalendar 요청");
        log.info("** calendarNameDTO: {}", calendarNameDTO);

        boolean result = calendarService.saveNewCalendarName(calendarNameDTO.getMemberId(), calendarNameDTO.getName());

        if (!result) {
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
