package com.nighthawk.spring_portfolio.Slack;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByDate(LocalDate date);
    List<CalendarEvent> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
