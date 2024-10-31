package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    // Save a new event
    public CalendarEvent saveEvent(CalendarEvent event) {
        return calendarEventRepository.save(event);
    }

    // Get events by a specific date
    public List<CalendarEvent> getEventsByDate(LocalDate date) {
        return calendarEventRepository.findByDate(date);
    }

    // Get events within a date range
    public List<CalendarEvent> getEventsWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return calendarEventRepository.findByDateBetween(startDate, endDate);
    }

    // Retrieve all events
    public List<CalendarEvent> getAllEvents() {
        return calendarEventRepository.findAll();
    }

    // Method to parse a Slack message and create calendar events
    public void parseSlackMessage(Map<String, String> jsonMap, LocalDate weekStartDate) {
        String text = jsonMap.get("text");
        List<CalendarEvent> events = extractEventsFromText(text, weekStartDate);
        for (CalendarEvent event : events) {
            saveEvent(event); // Save each parsed event
        }
    }

    // Extract events and calculate date for each day of the week
    private List<CalendarEvent> extractEventsFromText(String text, LocalDate weekStartDate) {
        List<CalendarEvent> events = new ArrayList<>();
        Pattern dayPattern = Pattern.compile("\\[(Mon|Tue|Wed|Thu|Fri|Sat|Sun)(?: - (Mon|Tue|Wed|Thu|Fri|Sat|Sun))?\\]: (.+)");
        Pattern descriptionPattern = Pattern.compile("- (.+)");

        String[] lines = text.split("\\n");
        String currentTitle = "";

        for (String line : lines) {
            Matcher dayMatcher = dayPattern.matcher(line);
            Matcher descMatcher = descriptionPattern.matcher(line);

            if (dayMatcher.find()) {
                String startDay = dayMatcher.group(1);
                String endDay = dayMatcher.group(2) != null ? dayMatcher.group(2) : startDay;
                currentTitle = dayMatcher.group(3).trim();

                // Calculate dates for each day in range and add events
                for (LocalDate date : getDatesInRange(startDay, endDay, weekStartDate)) {
                    events.add(new CalendarEvent(date, currentTitle, ""));
                }
            } else if (descMatcher.find() && !events.isEmpty()) {
                String description = descMatcher.group(1).trim();
                events.get(events.size() - 1).setDescription(description); // Add description to the last event
            }
        }
        return events;
    }

    // Helper to generate dates in the range [startDay - endDay]
    private List<LocalDate> getDatesInRange(String startDay, String endDay, LocalDate weekStartDate) {
        List<String> days = List.of("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        int startIndex = days.indexOf(startDay);
        int endIndex = days.indexOf(endDay);

        List<LocalDate> dateRange = new ArrayList<>();
        if (startIndex != -1 && endIndex != -1) {
            for (int i = startIndex; i <= endIndex; i++) {
                dateRange.add(weekStartDate.plusDays(i - weekStartDate.getDayOfWeek().getValue() + 1));
            }
        }
        return dateRange;
    }
}
