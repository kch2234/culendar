package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.domain.program.ProgramBkmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramBkMarkRepository extends JpaRepository<ProgramBkmark, Long> {

    ProgramBkmark findByMemberIdAndProgramId(Long memberId, Long programId);

    // 회원의 북마크 프로그램 최신순으로 무한 스크롤 조회
    @Query(value = "SELECT p.* FROM program p " +
        "JOIN program_bkmark pb ON p.id = pb.program_id " +
        "WHERE pb.member_id = :memberId " +
        "ORDER BY pb.created_date DESC " +
        "LIMIT :start, 20", // assuming 20 items per page
        nativeQuery = true)
    List<Program> findProgramBkmarkByMemberIdWithPaging(@Param("memberId") Long memberId, @Param("start") Long start);

}
