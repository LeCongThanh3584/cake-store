package com.example.demo.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateUtil {
    public static LocalDateTime getDateType(String dateType) {
        LocalDateTime time = LocalDateTime.now().toLocalDate().atStartOfDay();
        switch (dateType) {
            case "week":
                return time.minusWeeks(1);
            case "month":
                return time.withDayOfMonth(1);
            default:
                return time;
        }
    }

    public static String timeSince(LocalDateTime time) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(time, currentDateTime);

        if (duration.getSeconds() < 60) {
            return "just now";
        } else if (duration.toMinutes() < 60) {
            long minutes = duration.toMinutes();
            return minutes + (minutes > 1 ? " minutes ago" : " minute ago");
        } else if (duration.toHours() < 24) {
            long hours = duration.toHours();
            return hours + (hours > 1 ? " hours ago" : " hour ago");
        } else if (duration.toDays() < 7) {
            long days = duration.toDays();
            return days + (days > 1 ? " days ago" : " day ago");
        } else {
            long weeks = duration.toDays() / 7;
            return weeks + (weeks > 1 ? " weeks ago" : " week ago");
        }
    }
}
