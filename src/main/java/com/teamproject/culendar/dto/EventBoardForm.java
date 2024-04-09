package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EventBoardForm {

  private Long id;
  //  private String thumbnail; TODO 썸네일 추가
  private BoardType boardType;
  private Member member;
  private String title;
  private String content;
  //  private String image;  TODO 이미지 추가
  private Program program;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date eventDate;  // 일정 날짜

  //  private Location location; TODO 지역 추가
  private Integer maxPeople;  // 최대 정원
  private Gender filterGender;
  private Integer filterMinAge;
  private Integer filterMaxAge;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date deadlineDate;  // 일정 모집 마감 날짜

  private Boolean autoAccept;  // 자동 수락 여부

  public EventBoard toEntity() {
    EventBoard eventBoard = new EventBoard();
    eventBoard.setTitle(title);
    eventBoard.setContent(content);
//    eventBoard.setProgram(program);
//    eventBoard.setEventDate(eventDate);
    eventBoard.setMaxPeople(maxPeople);
    eventBoard.setFilterGender(filterGender);
    eventBoard.setFilterMinAge(filterMinAge);
    eventBoard.setFilterMaxAge(filterMaxAge);
//    eventBoard.setDeadlineDate(deadlineDate);
    eventBoard.setAutoAccept(autoAccept);
    return eventBoard;
  }


}
