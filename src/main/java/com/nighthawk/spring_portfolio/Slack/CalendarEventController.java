package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "*") // Allows cross-origin requests
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    // Fetch all events (GET /api/calendar)
    @GetMapping
    public List<CalendarEvent> getAllEvents() {
        return calendarEventService.getAllEvents();
    }

    // Fetch events by specific date (GET /api/calendar/events/{date})
    @GetMapping("/events/{date}")
    public List<CalendarEvent> getEventsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return calendarEventService.getEventsByDate(localDate);
    }

    // Fetch events within a date range (GET /api/calendar/events/range)
    @GetMapping("/events/range")
    public List<CalendarEvent> getEventsWithinDateRange(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return calendarEventService.getEventsWithinDateRange(startDate, endDate);
    }

}
