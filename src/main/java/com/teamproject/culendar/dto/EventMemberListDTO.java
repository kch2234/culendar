package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.board.EventMemberList;
import com.teamproject.culendar.domain.enumFiles.EventMemberStatus;
import lombok.Data;

@Data
public class EventMemberListDTO {
  private Long id;
  private EventBoardDTO eventBoardDTO;
  private MemberDTO memberDTO;
  private EventMemberStatus status;

  public EventMemberListDTO(EventMemberList eventMemberList) {
    this.id = eventMemberList.getId();
    this.eventBoardDTO = new EventBoardDTO(eventMemberList.getEventBoard());
    this.memberDTO = new MemberDTO(eventMemberList.getMember());
    this.status = eventMemberList.getStatus();
  }
}
