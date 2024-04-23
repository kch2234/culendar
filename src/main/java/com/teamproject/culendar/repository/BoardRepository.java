package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.member.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
  Page<Board> findByTitle(String title, Pageable pageable); // count 쿼리 사용
  List<Board> findByMember(String member, Pageable pageable);

  // 카테고리별 게시글 조회
  Page<Board> findByBoardType(BoardType boardType, Pageable pageable);

  // 전체 인기글 조회
    @Query("SELECT b " +
        "FROM Board b " +
        "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
        "GROUP BY b " +
        "ORDER BY COUNT(bb) DESC")
    Page<Board> findOrderByBkMark(Pageable pageable);

  // 카테고리별 인기글 조회
  @Query("SELECT b " +
      "FROM Board b " +
      "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
      "WHERE b.boardType = :boardType " +
      "GROUP BY b " +
      "ORDER BY COUNT(bb) DESC")
  Page<Board> findOrderByBoardType(@Param("boardType") BoardType boardType, Pageable pageable);

  // 최근 일주일 동안 최고 인기글 4개 조회
    @Query("SELECT b " +
        "FROM Board b " +
        "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
        "WHERE b.createDate >= CURRENT_DATE - 7 " +
        "GROUP BY b " +
        "ORDER BY COUNT(bb) DESC")
    List<Board> findBestByBkMark();

    // 작품의 programType Review 들중 최근 일주일 동안 가장 많이 북마크된 리뷰 4개 조회
    @Query("SELECT b " +
        "FROM Board b " +
        "LEFT JOIN BoardBkmark bb ON b.id = bb.board.id " +
        "WHERE b.program.id = :programId AND b.boardType = 'REVIEW' AND b.createDate >= CURRENT_DATE - 7 " +
        "GROUP BY b " +
        "ORDER BY COUNT(bb) DESC")
    List<Board> findBestReviewByBkMark(Long programId);

    // 회원의 REVIEW 게시글중 해당 프로그램의 리뷰가 있는지 조회
    @Query("SELECT b " +
        "FROM Board b " +
        "WHERE b.member.id = :memberId AND b.program.id = :programId AND b.boardType = 'REVIEW'")
    Board findProgramReviewByMemberId(Long memberId, Long programId);

    // 회원의 REVIEW 게시글 최신순으로 무한 스크롤 조회
  @Query(value = "SELECT * FROM board WHERE member_id = ?1 AND board_type = 'REVIEW' ORDER BY create_date DESC LIMIT ?2, 16", nativeQuery = true)
    List<Board> findProgramReviewByMemberIdWithPaging(Long memberId, Long start);

    // 회원의 INFO 게시글 최신순으로 무한 스크롤 조회
    @Query(value = "SELECT * FROM board WHERE member_id = ?1 AND board_type = 'INFO' ORDER BY create_date DESC LIMIT ?2, 16", nativeQuery = true)
    List<Board> findProgramInfoByMemberIdWithPaging(Long memberId, Long start);
}
