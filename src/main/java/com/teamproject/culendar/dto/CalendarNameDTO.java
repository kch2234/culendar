package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CalendarNameDTO {

    private Long id;
    private String name;
    private Member member;
    private Long memberId;

    public CalendarNameDTO(CalendarName calendarName) {
        this.id = calendarName.getId();
        this.name = calendarName.getName();
        this.member = calendarName.getMember();
    }

    public CalendarNameDTO(String name, Long memberId) {

        this.name = name;
        this.memberId = memberId;
    }
}
