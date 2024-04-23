package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Comment;
import com.teamproject.culendar.dto.PageRequestDTO;

import java.util.List;

public interface CommentCustomRepository {
  List<Comment> findCommentList(Long boardId, PageRequestDTO pageRequestDTO);
  int updateStep(Long refId, Integer step);
}