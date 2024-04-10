package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.program.ProgramBkmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramBkMarkRepository extends JpaRepository<ProgramBkmark, Long> {

    ProgramBkmark findByMemberIdAndProgramId(Long memberId, Long programId);
}
