package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.BoardType;
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

@RestController @Slf4j @RequiredArgsConstructor @RequestMapping("/ajaxClubs") public class AjaxClubController {

  private final EventBoardService eventBoardService;

  // 모임 전체 목록
  @GetMapping("/list/{sort}/{align}/{page}")
  public ResponseEntity<PageResponseDTO> list(@PathVariable("sort") String sort, @PathVariable("align") String align, @PathVariable("page") int page, Model model) {
    log.info("**** AjaxClubController GET /ajaxClubs/list/{}/{}/{} 요청", sort, align, page);
    PageRequestDTO pageRequestDTO = new PageRequestDTO(page, 8);
    Page<EventBoard> result = null;

    //프로그램 타입 셀렉터 함수
    if (sort.equals("DRAMA")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /DRAMA/BEST/{}", page);
        result = eventBoardService.getProgramTypeListWithBkMark(pageRequestDTO, ProgramType.DRAMA);  // 정보>인기글
      } else {
        log.info("**** AjaxClubController GET /DRAMA/NEW/{}", page);
        result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.DRAMA);  // 페이징
      }
    } else if (sort.equals("CONCERT")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /CONCERT/BEST/{}", page);
        result = eventBoardService.getProgramTypeListWithBkMark(pageRequestDTO, ProgramType.CONCERT);  // 후기>인기글
      } else {
        log.info("**** AjaxClubController GET /CONCERT/NEW/{}", page);
        result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.CONCERT);
      }
    } else if (sort.equals("EXHIBITION")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /EXHIBITION/BEST/{}", page);
        result = eventBoardService.getProgramTypeListWithBkMark(pageRequestDTO, ProgramType.EXHIBITION);  // 후기>인기글
      } else {
        log.info("**** AjaxClubController GET /EXHIBITION/NEW/{}", page);
        result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.EXHIBITION);
      }
    } else if (sort.equals("MUSICAL")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /MUSICAL/BEST/{}", page);
        result = eventBoardService.getProgramTypeListWithBkMark(pageRequestDTO, ProgramType.MUSICAL);  // 후기>인기글
      } else {
        log.info("**** AjaxClubController GET /MUSICAL/NEW/{}", page);
        result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.MUSICAL);
      }
    } else if (sort.equals("ETC")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /ETC/BEST/{}", page);
        result = eventBoardService.getProgramTypeListWithBkMark(pageRequestDTO, ProgramType.ETC);  // 후기>인기글
      } else {
        log.info("**** AjaxClubController GET /ETC/NEW/{}", page);
        result = eventBoardService.getListWithProgramType(pageRequestDTO, ProgramType.ETC);
      }
    } else if (sort.equals("ALL")) {
      if (align.equals("BEST")) {
        log.info("**** AjaxClubController GET /ALL/BEST/{}", page);
        result = eventBoardService.getListWithBkMark(pageRequestDTO);
      } else {
        log.info("**** AjaxClubController GET /ALL/NEW/{}", page);
        result = eventBoardService.getListWithPaging(pageRequestDTO);  // 페이징
      }
    }

    List<EventBoard> contents = result.getContent();  // Board 주소(정보)들
    List<EventBoardDTO> eventList = new ArrayList<>();
    for (int i = 0; i < contents.size(); i++) {
      EventBoard board = contents.get(i);  // Board 주소(정보) 1개
      EventBoardDTO dto = new EventBoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
      eventList.add(dto);  // 리스트에 추가
    }

    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());  // 페이징 노출
    pageResponseDTO.setEventList(eventList);


    return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
  }

}
