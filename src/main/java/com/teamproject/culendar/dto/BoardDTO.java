package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
  private Long id;
  private BoardType boardType;
  private Member member;
  private Program program;
  private String title;
  private String content;
  private Long viewCount;
//  private String image;  TODO 이미지 추가

  private LocalDateTime createDate;
  private LocalDateTime lastModifiedDate;

  public BoardDTO(Board board) {
    this.id = board.getId();
    this.member = board.getMember();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.viewCount = board.getViewCount();
    this.createDate = board.getCreateDate();
    this.lastModifiedDate = board.getLastModifiedDate();
  }

}
