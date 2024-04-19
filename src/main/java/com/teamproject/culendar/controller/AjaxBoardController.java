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
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ajaxBoards")
public class AjaxBoardController {

  private final BoardService boardService;

  // 게시글 전체 목록 (커뮤니티)
  @GetMapping("/list/{sort}/{page}")
  public ResponseEntity<PageResponseDTO> list(@PathVariable("sort") String sort, @PathVariable("page") int page){
    log.info("**** AjaxBoardController GET /ajaxBoards/list/{} : ", page);
    PageRequestDTO pageRequestDTO = new PageRequestDTO(page, 10);
      Page<Board> result = null;
    if (sort.equals("ALL")){
      result = boardService.getListWithPaging(pageRequestDTO);  // 페이징
    }
    else if(sort.equals("INFO")){
      result = boardService.getListWithCategory(pageRequestDTO, BoardType.INFO);
    }
    else if(sort.equals("REVIEW")){
      result = boardService.getListWithCategory(pageRequestDTO, BoardType.REVIEW);
    }
    else if(sort.equals("BEST")){
      result = boardService.getListWithBkMark(pageRequestDTO);
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
