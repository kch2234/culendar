package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.Comment;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.repository.BoardRepository;
import com.teamproject.culendar.repository.CommentRepository;
import com.teamproject.culendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  // 댓글 등록 : save()
  public Long addComment(CommentForm commentForm) {

    commentForm.setBoard(boardRepository.findById(commentForm.getBoardId()).get());
    log.info("***** CommentService - addComment setBoard : {}", String.valueOf(boardRepository.findById(commentForm.getBoardId()).get()));
    commentForm.setMember(memberRepository.findById(commentForm.getMemberId()).get());
    log.info("***** CommentService - addComment setMember : {}", String.valueOf(memberRepository.findById(commentForm.getMemberId())));

    Comment cmt = commentForm.toEntity();
    log.info("***** CommentService - addComment savedComentForm : {}", cmt);

    Comment saved = null; // 저장된 entity 담아줄 변수 미리 선언

    // 일반 댓글 : id=null, refId=0, step=0, level=0
    if(cmt.getRefId() == 0) {
      saved = commentRepository.save(cmt);// 저장!
      saved.setRefId(saved.getId()); // refId를 id값으로 수정 (그룹핑)

      // 댓글의 댓글 : id=null, refId=댓글다는대상id, step=0이상, level=0이상
    }else {
      // 이전 댓글들 모두 step + 1 하기 (밑으로 밀어서 공간 만들기)
      int updatedCount = commentRepository.updateStep(cmt.getRefId(), cmt.getStep());
      log.info("***** CommentService - Add - updatedCount : {}", updatedCount);
      cmt.setStep(cmt.getStep() + 1);
      cmt.setLevel(cmt.getLevel() + 1);
      saved = commentRepository.save(cmt);
    }
    log.info("***** CommentService - Add - saved : {}", saved);
    return saved.getId(); // 저장된 댓글의 고유번호 리턴
  }
  // 댓글 수정 : dirty checking
  public void modifyComment(CommentUpdate commentUpdate){
    Comment comment = commentRepository.findById(commentUpdate.getId()).orElse(null);
    assert comment != null;
    comment.setComment(commentUpdate.getComment());
  }

  // 댓글 삭제 : deleteById()
  public void removeComment(Long id) {
    commentRepository.deleteById(id);
  }

  // 댓글 1개 조회 : findById()
  public CommentDTO getOneComment(Long id) {
    Comment comment = commentRepository.findById(id).orElse(null);
    return new CommentDTO(comment);
  }


  // 댓글 목록 조회 (페이징처리X)  : CustomRepository
  public List<CommentDTO> getCommentList(Long boardId, PageRequestDTO pageRequestDTO) {
    List<Comment> entityList = commentRepository.findCommentList(boardId, pageRequestDTO);
    List<CommentDTO> list = new ArrayList<>();
    for(Comment c : entityList) {
      list.add(new CommentDTO(c));
    }
    return list;
  }
  // 댓글 목록 조회 (페이징처리O)
  public CommentResponseDTO getCommentListWithPaging(Long boardId, PageRequestDTO pageRequestDTO) {
    // 해당 댓글의 총개수 구하기
    Long totalCount = commentRepository.countAllByBoardId(boardId);
    log.info("**** CommentService getList - totalCount : {}", totalCount);
    PageResponseDTO pageResponseDTO = new PageResponseDTO(pageRequestDTO, totalCount);
    // 목록 가져오기
    List<Comment> entityList = commentRepository.findCommentList(boardId, pageRequestDTO);
    List<CommentDTO> list = new ArrayList<>();
    for(Comment c : entityList) {
      list.add(new CommentDTO(c));
    }
    return new CommentResponseDTO(pageResponseDTO, list);
  }



}