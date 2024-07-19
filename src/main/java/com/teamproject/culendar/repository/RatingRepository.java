package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.program.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // 평점 평균 구하기
    @Query("select avg(r.rating) from Rating r where r.program.id = :programId")
    Double getAvgRating(@Param("programId") Long programId);

    Rating findByMemberIdAndProgramId(Long memberId, Long programId);

    // 회원 문장형 평가 최신순으로 무한 스크롤 조회
    @Query(value = "SELECT * FROM rating WHERE member_id = ?1 ORDER BY create_date DESC LIMIT ?2, 16", nativeQuery = true)
    List<Rating> findMyRatingByMemberIdWithPaging(@Param("memberId")Long memberId, @Param("start")Long start);


}
