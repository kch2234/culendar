package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

@Data
public class BoardBkMarkDTO {

    private Long id;
    private BoardType boardType;
    private Long boardId;
    private Board board;
    private Long memberId;
    private Member member;
    private Long programId;
    private Program program;

    public BoardBkmark toEntity() {
        BoardBkmark boardBkmark = new BoardBkmark();

        boardBkmark.setId(this.id);
        boardBkmark.setBoardType(this.boardType);
        boardBkmark.setBoard(this.board);
        boardBkmark.setMember(this.member);
        boardBkmark.setProgram(this.program);

        return boardBkmark;
    }
}