package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController  // ajax 요청
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  // 댓글 추가
  @PostMapping("/add")
  public ResponseEntity<String> addComment(@RequestBody CommentForm commentForm) {
    log.info("**** CommentController POST /add - commentForm : {}", commentForm);
    Long result = commentService.addComment(commentForm);

    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  // 댓글 목록 조회
  @GetMapping("/list/{boardId}/{page}")
  public ResponseEntity<CommentResponseDTO> getList(
      @PathVariable("boardId") Long boardId,
      @PathVariable("page") int page) {

    log.info("***** CommentController GET /list - boardId : {}", boardId);
    log.info("***** CommentController GET /list - page : {}", page);
    // Board id에 해당하는 댓글들 중, 1페이지 10개 댓글 가져와줘~
    //List<CommentDTO> commentList = commentService.getCommentList(boardId, new PageRequestDTO(page, 10));
    CommentResponseDTO commentResponseDTO = commentService.getCommentListWithPaging(boardId, new PageRequestDTO(page, 10));
    log.info("***** CommentController GET /list - commentResponseDTO : {}", commentResponseDTO);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
  }

  // 댓글 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
    commentService.removeComment(id);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  // 댓글 1개 조회 요청
  @GetMapping("/{id}")
  public ResponseEntity<CommentDTO> getOneComment(@PathVariable("id") Long id) {
    log.info("***** CommentController GET /id : {}", id);
    CommentDTO comment = commentService.getOneComment(id);
    return new ResponseEntity<>(comment, HttpStatus.OK);
  }

  // 댓글 수정
  @PatchMapping("/{id}")
  public ResponseEntity<String> modifyComment(
      @PathVariable("id") Long id, @RequestBody CommentUpdate commentUpdate) {
    log.info("***** CommentController PATCH - id : {}", id);
    log.info("***** CommentController PATCH - commentUpdate : {}", commentUpdate);

    commentService.modifyComment(commentUpdate);

    return new ResponseEntity<>("success", HttpStatus.OK);
  }


}
