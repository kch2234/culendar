package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.repository.BoardRepository;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
  private Long id;
  private MemberDTO memberDTO;  // member 직접 사용 X
  private BoardType boardType;
  private ProgramDTO programDTO;
  private String title;
  private String content;
  private Long viewCount;
//  private String image;  TODO 이미지 추가

  private LocalDateTime createDate;
  private LocalDateTime lastModifiedDate;

  // Board entity -> BoardDTO 변환
  public BoardDTO(Board board) {
    this.id = board.getId();
    this.memberDTO = new MemberDTO(board.getMember());
    this.boardType = board.getBoardType();
    this.programDTO = new ProgramDTO(board.getProgram());
    this.title = board.getTitle();
    this.content = board.getContent();
    this.viewCount = board.getViewCount();
    this.createDate = board.getCreateDate();
    this.lastModifiedDate = board.getLastModifiedDate();
  }


}
