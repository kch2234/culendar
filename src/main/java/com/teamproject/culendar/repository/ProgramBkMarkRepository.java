package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.domain.program.ProgramBkmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramBkMarkRepository extends JpaRepository<ProgramBkmark, Long> {

    ProgramBkmark findByMemberIdAndProgramId(Long memberId, Long programId);

    // 회원의 북마크 프로그램 최신순으로 페이저블 조회
    @Query("SELECT p FROM Program p JOIN ProgramBkmark pb ON p.id = pb.program.id WHERE pb.member.id = :memberId ORDER BY pb.createDate DESC")
    Page<Program> findProgramBkmarkByMemberIdWithPaging(@Param("memberId") Long memberId, Pageable pageable);


}
