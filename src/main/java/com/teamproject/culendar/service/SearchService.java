package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.repository.EventBoardRepository;
import com.teamproject.culendar.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final ProgramRepository programRepository;
    private final EventBoardRepository eventBoardRepository;

    // 사용자 현위치 지역명을 Location enum으로 변환
    public Location mapStringToLocation(String locationString) {
        log.info("***** SearchService - mapStringToLocation ***** :{}", locationString);
        String normalizedLocation = normalizeLocationString(locationString);
        log.info("***** SearchService - mapStringToLocation - normalizedLocation :{}", normalizedLocation);
        try {
            log.info("***** SearchService - mapStringToLocation - Location.valueOf(normalizedLocation.toUpperCase()) :{}", Location.valueOf(normalizedLocation.toUpperCase()));
            return Location.valueOf(normalizedLocation.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("***** SearchService - mapStringToLocation - IllegalArgumentException :{}", e);
            return Location.ETC;  //매핑할 수 없는 경우 기타로 처리
        }
    }
    private String normalizeLocationString(String locationString) {
        log.info("***** SearchService - normalizeLocationString ***** :{}", locationString);
        switch(locationString) {
            case "서울": return "SEOUL";
            case "부산": return "BUSAN";
            case "대구": return "DAEGU";
            case "인천": return "INCHEON";
            case "광주": return "GWANGJU";
            case "대전": return "DAEJEON";
            case "울산": return "ULSAN";
            case "세종": return "SEJONG";
            case "경기": return "GYEONGGI";
            case "강원": return "GANGWON";
            case "충북": return "CHUNGBUK";
            case "충남": return "CHUNGNAM";
            case "전북": return "JEONBUK";
            case "전남": return "JEONNAM";
            case "경북": return "GYEONGBUK";
            case "경남": return "GYEONGNAM";
            case "제주": return "JEJU";
            case "기타": return "ETC";
            case "전체": return "ALL";
            default: return locationString;
        }
    }

    public List<Program> getBestTypeList(String programType, String locationType) {
        log.info("***** SearchService - getBestProgramList *****");
        // 분류가 전체 일 때
        if (programType.equals("ALL")) {
            Location locationENUM = Location.valueOf(locationType);
            List<Program> findPrograms = programRepository.findBestProgramsByLocation(locationENUM);
            return findPrograms;
        }
        // 지역이 전체 일 때
        else if (locationType.equals("ALL")) {
            ProgramType programTypeENUM = ProgramType.valueOf(programType);
            List<Program> findPrograms = programRepository.findBestProgramsByProgramType(programTypeENUM);
            return findPrograms;
        }
        // 분류 지역 둘 다 전체 일 때
        else if (programType.equals("ALL") && locationType.equals("ALL")) {
            List<Program> findPrograms = programRepository.findProgramsOrderByBkMarkCount();
            return findPrograms;

        }
        // 분류 지역 둘 다 전체가 아닐 때
        else {
            ProgramType programTypeENUM = ProgramType.valueOf(programType);
            Location locationENUM = Location.valueOf(locationType);
            List<Program> findPrograms = programRepository.findBestProgramsByProgramTypeAndLocation(programTypeENUM, locationENUM);
            return findPrograms;
        }
    }

    public List<EventBoard> getBestTypeEventBoardList(String programType, String locationType) {
        log.info("***** SearchService - getBestEventBoardList *****");
        // 분류가 전체 일 때
        if (programType.equals("ALL")) {
            Location locationENUM = Location.valueOf(locationType);
            List<EventBoard> findEventBoards = eventBoardRepository.findBestByLocationTypeList(locationENUM);
            return findEventBoards;
        }
        // 지역이 전체 일 때
        else if (locationType.equals("ALL")) {
            ProgramType programTypeENUM = ProgramType.valueOf(programType);
            List<EventBoard> findEventBoards = eventBoardRepository.findBestByProgramTypeList(programTypeENUM);
            return findEventBoards;
        }
        // 분류 지역 둘 다 전체 일 때
        else if (programType.equals("ALL") && locationType.equals("ALL")) {
            List<EventBoard> findEventBoards = eventBoardRepository.findBestList();
            return findEventBoards;
        }
        // 분류 지역 둘 다 전체가 아닐 때
        else {
            ProgramType programTypeENUM = ProgramType.valueOf(programType);
            Location locationENUM = Location.valueOf(locationType);
            List<EventBoard> findEventBoards = eventBoardRepository.findBestByProgramTypeAndLocList(programTypeENUM, locationENUM);
            return findEventBoards;
        }
    }
}
