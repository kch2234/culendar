package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarService {

    private final ProgramRepository programRepository;

    public List<Program> getProgramList(String programType, String location) {
        log.info("***** CalendarService - getProgramList *****");
        // 숫자를 ENUM으로 변환
        ProgramType programTypeENUM = ProgramType.valueOf(programType);
        Location locationENUM = Location.valueOf(location);
        List<Program> calendarDTO = programRepository.findProgramsByProgramTypeAndLocation(programTypeENUM, locationENUM);

        return calendarDTO;

    }


}
