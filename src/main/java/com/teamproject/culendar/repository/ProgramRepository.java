package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // seq 값으로 Program 찾기
    Optional<Program> findBySeq(Long seq);

    List<Program> findProgramsById(Long id);

    List<Program> findProgramsByProgramTypeAndLocation(ProgramType programType, Location location);

    List<Program> findProgramsByLocation(Location location);

    List<Program> findProgramsByProgramType(ProgramType programType);

    List<Program> findByTitleContaining(String keyword);
}
