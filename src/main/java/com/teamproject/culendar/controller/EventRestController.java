package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/eventBoard")
public class EventRestController {

    private final EventService eventService;

    @GetMapping("bestEventList")
    public ResponseEntity<List<EventBoardDTO>> bestEventProgramList() {
        log.info("** EventRestController GET /eventBoard/bestEventList");
        List<EventBoardDTO> eventBoardDTOList = eventService.bestEventList();
        // 만약 리스트가 비어있으면 null 반환
        if (eventBoardDTOList.size() == 0) {
            return ResponseEntity.ok(null);
        }
        // 리스트 첫번째 꺼내기
        EventBoardDTO eventBoardDTO = eventBoardDTOList.get(0);
        log.info("** EventRestController GET /eventBoard/bestEventList - eventBoardDTO: {}", eventBoardDTO.getId());

        return ResponseEntity.ok(eventBoardDTOList);
    }
}
