package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @PostMapping("/add")
    public void addEventsFromSlackMessage(@RequestBody String jsonString) {
        LocalDate weekStartDate = LocalDate.parse("2024-10-30"); // Example start date
        calendarEventService.parseSlackMessage(jsonString, weekStartDate);
    }

    @GetMapping("/events/{date}")
    public List<CalendarEvent> getEventsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return calendarEventService.getEventsByDate(localDate);
    }

    @GetMapping("/events")
    public List<CalendarEvent> getAllEvents() {
        return calendarEventService.getAllEvents();
    }

    @GetMapping("/events/range")
    public List<CalendarEvent> getEventsWithinDateRange(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return calendarEventService.getEventsWithinDateRange(startDate, endDate);
    }
}
