package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.program.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // 평점 평균 구하기
    @Query("select avg(r.rating) from Rating r where r.program.id = :programId")
    Double getAvgRating(@Param("programId") Long programId);
    Rating findByMemberIdAndProgramId(Long memberId, Long programId);

}
