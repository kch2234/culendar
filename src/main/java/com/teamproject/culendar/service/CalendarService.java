package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.CalendarNameDTO;
import com.teamproject.culendar.repository.CalendarNameRepository;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarService {

    private final ProgramRepository programRepository;
    private final CalendarNameRepository calendarNameRepository;
    private final MemberRepository memberRepository;

    public List<Program> getProgramList(String programType, String location) {
        log.info("***** CalendarService - getProgramList *****");
        // 숫자를 ENUM으로 변환
        ProgramType programTypeENUM = ProgramType.valueOf(programType);
        Location locationENUM = Location.valueOf(location);
        List<Program> calendarDTO = programRepository.findProgramsByProgramTypeAndLocation(programTypeENUM, locationENUM);

        return calendarDTO;

    }

    public List<CalendarNameDTO> getCalendarList(Long id) {
        log.info("***** CalendarService - getCalendarList *****");
        List<CalendarName> calendarNamesByMemberId = calendarNameRepository.findCalendarNamesByMemberId(id);
        List<CalendarNameDTO> calendarNameDTO = calendarNamesByMemberId.stream()
                .map(CalendarNameDTO::new)
                .collect(Collectors.toList());
        log.info("***** CalendarService - getCalendarList - calendarNameDTO : {}", calendarNameDTO);

        return calendarNameDTO;

    }

    public void saveCalendarName(Long id) {
        log.info("***** CalendarService - saveCalendarName *****");
        CalendarName calendarName = new CalendarName();
        calendarName.setName("나의 캘린더");
        memberRepository.findById(id).ifPresent(calendarName::setMember);
        calendarNameRepository.save(calendarName);
    }


}
