package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Comment;
import com.teamproject.culendar.dto.PageRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Comment> findCommentList(Long boardId, PageRequestDTO pageRequestDTO) {
    int offset = (pageRequestDTO.getPage() - 1) * 10;
    List<Comment> list = em.createQuery("select c from Comment c where c.board.id = :boardId order by c.refId desc, c.step asc",
            Comment.class)
        .setParameter("boardId", boardId)
        .setFirstResult(offset)
        .setMaxResults(pageRequestDTO.getSize())
        .getResultList();
    return list;
  }

  @Override
  public int updateStep(Long refId, Integer step) {
    int updatedCount = em.createQuery(
            "update Comment c set c.step = c.step + 1 where refId = :refId and step > :step")
        .setParameter("refId", refId)
        .setParameter("step", step)
        .executeUpdate();
    return updatedCount;
  }
}

