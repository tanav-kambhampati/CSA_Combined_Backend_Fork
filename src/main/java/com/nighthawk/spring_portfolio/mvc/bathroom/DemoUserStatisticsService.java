package com.nighthawk.spring_portfolio.mvc.bathroom;


import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUser;
import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.temporal.WeekFields;

@Service
public class DemoUserStatisticsService {

    @Autowired
    private DemoUserRepository demoUserRepository;

    // 1. Calculate total duration of a week
    public Duration getTotalDurationForWeek(Long userId, int year, int week) {
        List<DemoUser> userSessions = getSessionsForWeek(userId, year, week);
        return userSessions.stream()
                .map(DemoUser::getSessionDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    // 2. Calculate total occurrences in a week
    public long getTotalOccurrencesForWeek(Long userId, int year, int week) {
        return getSessionsForWeek(userId, year, week).size();
    }

    // 3. Calculate average duration per day
    public Duration getAverageDurationPerDay(Long userId, int year, int week) {
        Map<LocalDate, List<DemoUser>> sessionsByDay = groupSessionsByDay(userId, year, week);
        return sessionsByDay.values().stream()
                .map(daySessions -> daySessions.stream()
                        .map(DemoUser::getSessionDuration)
                        .reduce(Duration.ZERO, Duration::plus))
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(sessionsByDay.size());
    }

    // 4. Calculate occurrences per day
    public Map<LocalDate, Long> getOccurrencesPerDay(Long userId, int year, int week) {
        return groupSessionsByDay(userId, year, week).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (long) entry.getValue().size()
                ));
    }

    // Helper method to get sessions for a specific week
    private List<DemoUser> getSessionsForWeek(Long userId, int year, int week) {
        return demoUserRepository.findByUserId(userId).stream()
                .filter(user -> user.getLoginDate() != null
                        && user.getLoginDate().getYear() == year
                        && user.getLoginDate().get(WeekFields.ISO.weekOfYear()) == week)
                .collect(Collectors.toList());
    }

    // Helper method to group sessions by day
    private Map<LocalDate, List<DemoUser>> groupSessionsByDay(Long userId, int year, int week) {
        return getSessionsForWeek(userId, year, week).stream()
                .collect(Collectors.groupingBy(DemoUser::getLoginDate));
    }
}

