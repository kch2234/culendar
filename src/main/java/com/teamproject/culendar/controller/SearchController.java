package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.repository.ProgramRepository;
import com.teamproject.culendar.service.EventBoardService;
import com.teamproject.culendar.service.ProgramService;
import com.teamproject.culendar.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final ProgramService programService;
    private final SearchService searchService;
    private final EventBoardService eventBoardService;
    private final ProgramRepository programRepository;

    // programType, location에 해당하는 인기 있는 작품리스트를 가져옴
    @GetMapping("bestProgramTypeList")
    public ResponseEntity<List<ProgramDTO>> getBestProgramList(@RequestParam("programType") String programType, @RequestParam("locationType") String locationType) {
        log.info("** SearchController GET /locationList 요청");
        log.info("** /locationList programType: {}", programType);
        log.info("** /locationList location: {}", locationType);
        List<ProgramDTO> programDTOList = programService.getBestProgramLocList(programType, locationType);
        return ResponseEntity.ok(programDTOList);
    }

    // ProgramType, LocationType에 해당하는 인기 있는 모임리스트를 가져옴
    @GetMapping("bestEventBoardList")
    public ResponseEntity<List<EventBoardDTO>> getBestEventBoardList(@RequestParam("programType") String programType, @RequestParam("locationType") String locationType) {
        log.info("** SearchController GET /bestEventBoardList 요청");
        log.info("** /bestEventBoardList programType: {}", programType);
        log.info("** /bestEventBoardList location: {}", locationType);
        List<EventBoardDTO> eventBoardList = eventBoardService.getBestEventBoardLocList(programType, locationType);
        return ResponseEntity.ok(eventBoardList);
    }

    // 사용자 위치 기반으로 추천하는 작품 리스트
    @GetMapping("locationBestList")
    public ResponseEntity<List<ProgramDTO>> getLocationBestList(@RequestParam("location") String locationString){
        log.info("***** SearchController - getLocationBestList ***** :{}", locationString);
        Location location = searchService.mapStringToLocation(locationString);
        log.info("***** SearchController - getLocationBestList - location :{}", location);
        List<Program> programList = programRepository.findCurrentLocationByBestPrograms(location);
        List<ProgramDTO> programDTOList = programList.stream()
                .map(ProgramDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(programDTOList);
    }
}
