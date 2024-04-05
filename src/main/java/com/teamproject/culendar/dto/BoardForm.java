package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

@Data
public class BoardForm {

  private Long id;
  private BoardType boardType;
  private Member member;
  private Program program;
  private String title;
  private String content;
//  private String image;  TODO 이미지 추가

  public Board toEntity() {
    Board board = new Board();
    board.setTitle(title);
    board.setContent(content);
    return board;
  }

}
