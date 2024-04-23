package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.Comment;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

// 댓글 등록 시 댓글폼 정보 담아주는 용도
@Data
public class CommentForm {
  private Long id;
  private BoardDTO boardDTO;
  private MemberDTO memberDTO;
  private String comment;
  private Long refId;
  private Integer step;
  private Integer level;

  // Form -> Entity
  public Comment toEntity() {
    Comment cmt = new Comment();
    cmt.setComment(comment);
    cmt.setMember(memberDTO.toEntity());
    cmt.setRefId(refId);
    cmt.setStep(step);
    cmt.setLevel(level);
    return cmt;
  }

}
