package com.teamproject.culendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentResponseDTO {
  private PageResponseDTO pageResponseDTO; // 댓글 페이징처리 정보
  private List<CommentDTO> commentList;  // 댓글 목록
}
