package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // seq 값으로 Program 찾기
    Optional<Program> findBySeq(Long seq);
}
