package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.Comment;
import com.teamproject.culendar.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

// 댓글을 화면에 전달(응답)해주는 용도로 사용
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
  private Long id;
  private String comment;
  private MemberDTO memberDTO;
  private LocalDateTime createDate;
  private LocalDateTime lastModifiedDate;
  private Long refId;
  private Integer step;
  private Integer level;

  // Entity -> CommentDTO
  public CommentDTO(Comment entity) {
    id = entity.getId();
    comment = entity.getComment();
    memberDTO = new MemberDTO();
    createDate = entity.getCreateDate();
    lastModifiedDate = entity.getLastModifiedDate();
    refId = entity.getRefId();
    step = entity.getStep();
    level = entity.getLevel();
  }

}

