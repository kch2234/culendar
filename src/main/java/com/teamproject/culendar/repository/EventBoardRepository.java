package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.EventBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {

  Page<EventBoard> findByTitle(String title, Pageable pageable);
  List<EventBoard> findByMember(String member, Pageable pageable);

  Long findEventBoardIdById(Long id);

  // 프로그램타입별 모임 조회
  @Query("SELECT eb FROM EventBoard eb JOIN eb.program p WHERE p.programType = :programType")
  Page<EventBoard> findByProgramType(@Param("programType") ProgramType programType, Pageable pageable);

  // 전체 인기 모임 조회
  @Query("SELECT b " +
      "FROM EventBoard b " +
      "LEFT JOIN EventBoardBkmark bb ON b.id = bb.eventBoard.id " +
      "GROUP BY b " +
      "ORDER BY COUNT(bb) DESC")
  Page<EventBoard> findOrderByBkMark(Pageable pageable);

  // 프로그램타입별 인기글 조회 (현재 모임 북마크 기능 숨겨둬서 추후 수정 필요 - 임시로 디비에 북마크 데이터 넣음)
  @Query("SELECT b " +
      "FROM EventBoard b " +
      "LEFT JOIN EventBoardBkmark bb ON b.id = bb.eventBoard.id " +
      "LEFT JOIN b.program p " +
      "WHERE p.programType = :programType " +
      "GROUP BY b " +
      "ORDER BY COUNT(bb) DESC")
  Page<EventBoard> findOrderByProgramType(@Param("programType") ProgramType programType, Pageable pageable);

  // programType, locationType 해당하는 인기 모임 조회
  @Query("SELECT b FROM EventBoard b " +
    "LEFT JOIN Program p ON b.program.id = p.id " +
    "WHERE p.programType = :programType " +
    "AND p.location = :locationType " +
    "GROUP BY b order by COUNT(b) DESC")
  List<EventBoard> findBestByProgramTypeAndLocList(@Param("programType") ProgramType programType, @Param("locationType") Location locationType);

  // programType 해당하는 인기 모임 조회
  @Query("SELECT b FROM EventBoard b " +
    "LEFT JOIN Program p ON b.program.id = p.id " +
    "WHERE p.programType = :programType " +
    "GROUP BY b order by COUNT(b) DESC")
  List<EventBoard> findBestByProgramTypeList(@Param("programType") ProgramType programType);

  // locationType 해당하는 인기 모임 조회
  @Query("SELECT b FROM EventBoard b " +
    "LEFT JOIN Program p ON b.program.id = p.id " +
    "WHERE p.location = :locationType " +
    "GROUP BY b order by COUNT(b) DESC")
  List<EventBoard> findBestByLocationTypeList(@Param("locationType") Location locationType);

  // 전체 인기 모임 조회
  @Query("SELECT b FROM EventBoard b " +
    "LEFT JOIN EventBoardBkmark bb ON b.id = bb.eventBoard.id " +
    "GROUP BY b order by COUNT(b) DESC")
  List<EventBoard> findBestList();
}