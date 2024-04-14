package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.calendar.CalendarName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarNameRepository extends JpaRepository<CalendarName, Long> {
    List<CalendarName> findCalendarNamesByMemberId(Long id);
}
