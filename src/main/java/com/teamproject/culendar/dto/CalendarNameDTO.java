package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

@Data
public class CalendarNameDTO {

    private Long id;
    private String name;
    private Member member;

    public CalendarNameDTO(CalendarName calendarName) {
        this.id = calendarName.getId();
        this.name = calendarName.getName();
        this.member = calendarName.getMember();
    }
}
