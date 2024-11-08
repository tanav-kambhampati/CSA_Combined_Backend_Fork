package com.nighthawk.spring_portfolio.mvc.bathroom;

import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUser;
import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUserRepository;
import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUserStatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class DemoAdminController {

    @Autowired
    private DemoUserRepository demoUserRepository;

    @Autowired
    private DemoUserStatisticsService demoUserStatisticsService;

    @GetMapping("/demo_admin")
    public String getUsers(Model model) {
        // Get all users from the database
        List<DemoUser> users = demoUserRepository.findAll();
        model.addAttribute("users_history", users);

        // Get the current year and week
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentWeek = currentDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        // Initialize maps to store statistics for each user
        //Map<Long, Duration> totalDurationMap = new HashMap<>();
        // Map<Long, Long> totalOccurrencesMap = new HashMap<>();
        // Map<Long, Duration> avgDurationPerDayMap = new HashMap<>();
        // Map<Long, Map<String, Long>> occurrencesPerDayMap = new HashMap<>();
        Map<String, String> totalDurationMap = new HashMap<>();
        Map<String, Long> totalOccurrencesMap = new HashMap<>();
        Map<String, String> avgDurationPerDayMap = new HashMap<>();
        Map<String, Map<String, Long>> occurrencesPerDayMap = new HashMap<>();

        // Calculate statistics for each user and populate maps
        for (DemoUser user : users) {
            Long userId = user.getUserId();
            String userName = user.getUserName();

            Duration totalDuration = demoUserStatisticsService.getTotalDurationForWeek(userId, currentYear, currentWeek);
            long totalOccurrences = demoUserStatisticsService.getTotalOccurrencesForWeek(userId, currentYear, currentWeek);
            Duration avgDurationPerDay = demoUserStatisticsService.getAverageDurationPerDay(userId, currentYear, currentWeek);
            Map<LocalDate, Long> occurrencesPerDay = demoUserStatisticsService.getOccurrencesPerDay(userId, currentYear, currentWeek);

            // Convert Duration to HH:mm:ss format
            totalDurationMap.put(userName, formatDurationInMinutes(totalDuration));
            // avgDurationPerDayMap.put(userName, formatDuration(avgDurationPerDay));
            avgDurationPerDayMap.put(userName, formatDurationInMinutes(avgDurationPerDay));
            totalOccurrencesMap.put(userName, totalOccurrences);

            // Map LocalDate occurrences to weekday names for better display in the table
            Map<String, Long> weekdayOccurrences = new HashMap<>();
            for (LocalDate date : occurrencesPerDay.keySet()) {
                String dayName = date.getDayOfWeek().toString();
                weekdayOccurrences.put(dayName, occurrencesPerDay.get(date));
            }

            // Add statistics to corresponding maps
            // totalDurationMap.put(userId, totalDuration);
            // totalOccurrencesMap.put(userId, totalOccurrences);
            // avgDurationPerDayMap.put(userId, avgDurationPerDay);
            // occurrencesPerDayMap.put(userId, weekdayOccurrences);

            // totalDurationMap.put(userName, totalDuration);
            // totalOccurrencesMap.put(userName, totalOccurrences);
            // avgDurationPerDayMap.put(userName, avgDurationPerDay);
            // occurrencesPerDayMap.put(userName, weekdayOccurrences);
        }

        // Add statistics to the model
        model.addAttribute("totalDurationMap", totalDurationMap);
        model.addAttribute("avgDurationPerDayMap", avgDurationPerDayMap);
        model.addAttribute("totalOccurrencesMap", totalOccurrencesMap);
        model.addAttribute("occurrencesPerDayMap", occurrencesPerDayMap);

        return "demo_adminTable";
    }

    // Helper method to format Duration as HH:mm:ss
    // private String formatDuration(Duration duration) {
    //     long hours = duration.toHours();
    //     long minutes = duration.toMinutesPart();
    //     long seconds = duration.toSecondsPart();
    //     return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    // }

    // Helper method to format Duration as minutes
    private String formatDurationInMinutes(Duration duration) {
        long minutes = duration.toMinutes();
        return String.format("%d", minutes);
    }

}
