package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // seq 값으로 Program 찾기
    Optional<Program> findBySeq(Long seq);

    Program findProgramsById(Long id);

    List<Program> findProgramsByProgramTypeAndLocation(ProgramType programType, Location location);

    List<Program> findProgramsByLocation(Location location);

    List<Program> findProgramsByProgramType(ProgramType programType);

    List<Program> findByTitleContaining(String keyword);

    // 최근 일주일 동안 북마크 최고 작품 4개 조회
    @Query("SELECT p " +
            "FROM Program p " +
            "LEFT JOIN ProgramBkmark pb ON p.id = pb.program.id " +
            "WHERE pb.createDate >= CURRENT_DATE - 7 " +
            "GROUP BY p " +
            "ORDER BY COUNT(pb) DESC")
    List<Program> findProgramsOrderByBkMarkCount();

    // programType, locationType 해당하는 인기 있는 작품리스트를 가져옴
    @Query("SELECT p FROM Program p " +
            "LEFT JOIN ProgramBkmark pb ON p.id = pb.program.id " +
            "WHERE (p.programType = :programType) " +
            "AND (p.location = :locationType) " +
            "AND pb.createDate >= CURRENT_DATE - 7 " +
            "GROUP BY p " +
            "ORDER BY COUNT(pb) DESC")
    List<Program> findBestProgramsByProgramTypeAndLocation(ProgramType programType, Location locationType);

    // 사용자 위치 기반으로 추천하는 작품리스트
    @Query("SELECT p FROM Program p " +
            "LEFT JOIN ProgramBkmark pb ON p.id = pb.program.id " +
            "WHERE (:location IS NULL OR p.location = :location) " +
            "AND pb.createDate >= CURRENT_DATE - 7 " +
            "GROUP BY p " +
            "ORDER BY COUNT(pb) DESC")
    List<Program> findCurrentLocationByBestPrograms(Location location);
}
