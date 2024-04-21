package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.repository.ProgramRepository;
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
    private final ProgramRepository programRepository;


    @GetMapping("bestProgramTypeList")
    public ResponseEntity<List<ProgramDTO>> getBestProgramList(@RequestParam("programType") String programType, @RequestParam("locationType") String locationType) {
        log.info("** SearchController GET /locationList 요청");
        log.info("** programType: {}", programType);
        log.info("** location: {}", locationType);
        // programType, location에 해당하는 인기 있는 작품리스트를 가져옴
        List<ProgramDTO> programDTOList = programService.getBestProgramLocList(programType, locationType);
        return ResponseEntity.ok(programDTOList);
    }

    // 사용자 위치 기반으로 추천하는 작품 리스트
    @GetMapping("locationBestList")
    public ResponseEntity<List<ProgramDTO>> getLocationBestList(@RequestParam("location") String locationString){
        log.info("***** SearchController - getLocationBestList ***** :{}", locationString);
        Location location = searchService.mapStringToLocation(locationString);
        log.info("***** SearchController - getLocationBestList - location :{}", location);
        List<Program> programList = programRepository.findProgramsByLocation(location);
        List<ProgramDTO> programDTOList = programList.stream()
                .map(ProgramDTO::new)
                .collect(Collectors.toList());
        log.info("***** SearchController - getLocationBestList - programDTOList :{}", programDTOList);
        return ResponseEntity.ok(programDTOList);
    }
}
