package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.calendar.CalendarContent;
import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

@Data
public class CalendarContentDTO {

    private Long id;
    private Long calendarId;
    private CalendarName calendarName;
    private Long programId;
    private Program program;

    public CalendarContent toEntity(){
        CalendarContent calendarContent = new CalendarContent();
        calendarContent.setCalendarName(this.calendarName);
        calendarContent.setProgram(this.program);

        return calendarContent;
    }
}
