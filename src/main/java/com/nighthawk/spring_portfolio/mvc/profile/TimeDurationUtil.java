package com.nighthawk.spring_portfolio.mvc.profile;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeDurationUtil {

    // Method to calculate the duration as a Duration object
    public static Duration getTimeDuration(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn != null && timeOut != null) {
            return Duration.between(timeIn, timeOut);
        }
        return Duration.ZERO; // Return zero if either timeIn or timeOut is not set
    }

    // Method to calculate human-readable duration
    public static String getReadableDuration(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn != null && timeOut != null) {
            Duration duration = Duration.between(timeIn, timeOut);
            long hours = duration.toHours();
            long minutes = duration.minus(hours, ChronoUnit.HOURS).toMinutes();
            return hours + " hour" + (hours != 1 ? "s" : "") + " " + minutes + " minute" + (minutes != 1 ? "s" : "");
        }
        return "0 minutes";
    }

    // Method to get duration in minutes
    public static long getDurationInMinutes(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn != null && timeOut != null) {
            return Duration.between(timeIn, timeOut).toMinutes();
        }
        return 0;
    }
}
