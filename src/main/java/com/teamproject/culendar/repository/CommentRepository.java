package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

  // 댓글 개수
  Long countAllByBoardId(Long boardId);

  @Modifying
  @Transactional
  @Query("UPDATE Comment c SET c.step = c.step + 1 WHERE c.refId = :refId AND c.step >= :step")
  int updateStep(Long refId, Integer step);
}
