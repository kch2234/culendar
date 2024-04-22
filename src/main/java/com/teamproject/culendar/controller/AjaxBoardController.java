package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.PageRequestDTO;
import com.teamproject.culendar.dto.PageResponseDTO;
import com.teamproject.culendar.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ajaxBoards")
public class AjaxBoardController {

  private final BoardService boardService;

  // 게시글 전체 목록 (커뮤니티)
  @GetMapping("/list/{sort}/{align}/{page}")
  public ResponseEntity<PageResponseDTO> list(@PathVariable("sort") String sort, @PathVariable("align") String align, @PathVariable("page") int page){
    log.info("**** AjaxBoardController GET /ajaxBoards/list/{}/{}/{} 요청", sort, align, page);
    PageRequestDTO pageRequestDTO = new PageRequestDTO(page, 10);

    Page<Board> result = null;

    if (sort.equals("ALL")){
      if(align.equals("BEST")){
        log.info("***** AjaxBoardController get /list/ALL/BEST/{}", page);
        result = boardService.getListWithBkMark(pageRequestDTO);
      } else {
        log.info("***** AjaxBoardController get /list/ALL/NEW/{}", page);
          result = boardService.getListWithPaging(pageRequestDTO);  // 페이지 첫 로드 -- 전체>최신글
      }
    }

    else if(sort.equals("INFO")){
      if(align.equals("BEST")){
        log.info("***** AjaxBoardController get /list/INFO/BEST/{}", page);
        result = boardService.getCategoryListWithBkMark(pageRequestDTO, BoardType.INFO);  // 정보>인기글
      }
      else {
        log.info("***** AjaxBoardController get /list/INFO/NEW/{}", page);
          result = boardService.getListWithCategory(pageRequestDTO, BoardType.INFO);  // 정보 카테고리 첫 로드 -- 정보>최신글
      }
    }


    else if(sort.equals("REVIEW")){
      if(align.equals("BEST")){
        log.info("***** AjaxBoardController get /list/REVIEW/BEST/{}", page);
        result = boardService.getCategoryListWithBkMark(pageRequestDTO, BoardType.REVIEW);  // 후기>인기글
      }
      else {
        log.info("***** AjaxBoardController get /list/REVIEW/NEW/{}", page);
          result = boardService.getListWithCategory(pageRequestDTO, BoardType.REVIEW);  // 후기 카테고리 첫 로드 -- 후기>최신글
      }
    }

    List<Board> contents = result.getContent();  // Board 주소(정보)들
    List<BoardDTO> list = new ArrayList<>();
    for(int i = 0; i < contents.size(); i++) {
      Board board = contents.get(i);  // Board 주소(정보) 1개
      BoardDTO dto = new BoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
      list.add(dto);  // 리스트에 추가
    }
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, result.getTotalElements());  // 페이징 노출

    pageResponseDTO.setList(list);

    return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
  }


    // 최고 인기 게시글 4개 조회
    @GetMapping("/best")
    public ResponseEntity<List<BoardDTO>> bestList() {
        log.info("**** AjaxBoardController GET /ajaxBoards/best");
        List<Board> result = boardService.getBestList();
        List<BoardDTO> list = new ArrayList<>();
        for(int i = 0; i < result.size(); i++) {
            Board board = result.get(i);  // Board 주소(정보) 1개
            BoardDTO dto = new BoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
            list.add(dto);  // 리스트에 추가
        }
        log.info("**** AjaxBoardController GET /ajaxBoards/best - list : {}", list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 해당 작품 최고 인기 리뷰 4개 조회
    @GetMapping("/bestReviewList/{id}")
    public ResponseEntity<List<BoardDTO>> bestList(@PathVariable("id") Long id) {
        log.info("**** AjaxBoardController GET /ajaxBoards/bestReviewList/{}", id);
        List<Board> result = boardService.getBestReviewList(id);
        List<BoardDTO> list = new ArrayList<>();
        for(int i = 0; i < result.size(); i++) {
            Board board = result.get(i);  // Board 주소(정보) 1개
            BoardDTO dto = new BoardDTO(board);  // BoardDTO 정보 1개 - MemberDTO 포함
            list.add(dto);  // 리스트에 추가
        }
        log.info("**** AjaxBoardController GET /ajaxBoards/best - list : {}", list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
