package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.calendar.CalendarContent;
import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.CalendarContentDTO;
import com.teamproject.culendar.dto.CalendarNameDTO;
import com.teamproject.culendar.repository.CalendarContentRepository;
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
    private final CalendarContentRepository calendarContentRepository;

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

    public boolean saveNewCalendarName(Long id, String name) {
        log.info("***** CalendarService - saveNewCalendarName *****");
        CalendarName calendarName = new CalendarName();
        calendarName.setName(name);
        memberRepository.findById(id).ifPresent(calendarName::setMember);

        //중복 체크
        List<CalendarName> calendarNames = calendarNameRepository.findCalendarNamesByMemberId(id);
        for (CalendarName calendarName1 : calendarNames) {
            if (calendarName1.getName().equals(name)) {
                return false;
            }
        }
        calendarNameRepository.save(calendarName);
        return true;

    }

    public boolean saveCalendarContent(CalendarContentDTO calendarContentDTO) {
        log.info("***** CalendarService - saveCalendarContent *****");

        calendarContentDTO.setCalendarName(calendarNameRepository.findById(calendarContentDTO.getCalendarId()).get());
        calendarContentDTO.setProgram(programRepository.findById(calendarContentDTO.getProgramId()).get());

        //중복 체크
        CalendarContent calendarContents = calendarContentRepository.findCalendarContentsByCalendarNameAndProgram(calendarContentDTO.getCalendarName(), calendarContentDTO.getProgram());
        if (calendarContents != null) {
            return false;
        }

        calendarContentRepository.save((calendarContentDTO.toEntity()));

        return true;
    }


}
