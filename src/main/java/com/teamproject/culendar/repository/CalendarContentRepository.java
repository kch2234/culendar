package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.calendar.CalendarContent;
import com.teamproject.culendar.domain.calendar.CalendarName;
import com.teamproject.culendar.domain.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarContentRepository extends JpaRepository<CalendarContent, Long> {

    CalendarContent findCalendarContentsByCalendarNameAndProgram(CalendarName calendarName, Program program);
}
