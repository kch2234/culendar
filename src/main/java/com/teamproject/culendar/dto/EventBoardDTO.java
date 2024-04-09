package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventBoardDTO {
  private Long id;
//  private String thumbnail; TODO 썸네일 추가
  private String title;
  private String content;
//  private String image;  TODO 이미지 추가
  private Member member;
  private Program program;
  private LocalDateTime eventDate;  // 모임 날짜
  private Integer maxPeople;  // 최대 정원
//  private Location location; TODO 지역 추가
  private Gender filterGender;
  private Integer filterMinAge;
  private Integer filterMaxAge;
  private LocalDateTime deadlineDate;  // 모임 모집 마감 날짜
  private Boolean autoAccept;  // 자동 수락 여부

  private LocalDateTime createDate;
  private LocalDateTime lastModifiedDate;

  public EventBoardDTO(EventBoard eventBoard) {
    this.id = eventBoard.getId();
    this.title = eventBoard.getTitle();
    this.content = eventBoard.getContent();
    this.member = eventBoard.getMember();
    this.program = eventBoard.getProgram();
    this.eventDate = eventBoard.getEventDate();
    this.maxPeople = eventBoard.getMaxPeople();
    this.filterGender = eventBoard.getFilterGender();
    this.filterMinAge = eventBoard.getFilterMinAge();
    this.filterMaxAge = eventBoard.getFilterMaxAge();
    this.deadlineDate = eventBoard.getDeadlineDate();
    this.autoAccept = eventBoard.getAutoAccept();
    this.createDate = eventBoard.getCreateDate();
    this.lastModifiedDate = eventBoard.getLastModifiedDate();
  }

}


