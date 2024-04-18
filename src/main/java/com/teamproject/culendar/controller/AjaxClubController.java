package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.service.EventBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ajaxClubs")
public class AjaxClubController {

  private final EventBoardService eventBoardService;

  // 모임 전체 목록
  @GetMapping("/list/{sort}/{page}")
  public ResponseEntity<PageResponseDTO> list(@PathVariable("sort") String sort, @PathVariable("page") int page, Model model){
    log.info("**** AjaxClubController GET /ajaxClubs/list/{} : ", page);
    PageRequestDTO pageRequestDTO = new PageRequestDTO(page, 10);
    Page<EventBoard> result = eventBoardService.getListWithPaging(pageRequestDTO);
    if (sort.equals("ALL")){
      result = eventBoardService.getListWithPaging(pageRequestDTO);  // 페이징
    }
    else if (sort.equals("DRAMA")){
      result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.DRAMA);  // 페이징
    }
    else if(sort.equals("CONCERT")){
      result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.CONCERT);
    }
    else if(sort.equals("EXHIBITION")){
      result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.EXHIBITION);
    }
    else if(sort.equals("MUSICAL")){
      result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.MUSICAL);
    }
    else if(sort.equals("ETC")){
      result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.ETC);
    }
    else if(sort.equals("BEST")){
      result = eventBoardService.getListWithBkMark(pageRequestDTO);
    }

    List<EventBoard> contents = result.getContent();  // Board 주소(정보)들
    List<EventBoardDTO> eventList = new ArrayList<>();
    for(int i = 0; i < contents.size(); i++) {
      EventBoard board = contents.get(i);  // Board 주소(정보) 1개
      EventBoardDTO dto = new EventBoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
      eventList.add(dto);  // 리스트에 추가
    }
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());  // 페이징 노출
    pageResponseDTO.setEventList(eventList);


    return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
  }

}
