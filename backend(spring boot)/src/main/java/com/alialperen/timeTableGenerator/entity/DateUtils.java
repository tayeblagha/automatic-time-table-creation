package com.alialperen.timeTableGenerator.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateUtils {

    public static LocalDate getPreviousMondayOrTodayIfMonday() {
        LocalDate today = LocalDate.now();

        return today.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getStrictlyPreviousWeekMonday() {
        LocalDate today = LocalDate.now();
        // Subtract 7 days if today is Monday, otherwise adjust to the previous Monday
        return today.getDayOfWeek() == DayOfWeek.MONDAY
                ? today.minusWeeks(1)
                : today.with(DayOfWeek.MONDAY).minusDays(7);
    }
    public static boolean isDateEqualToCurrentDate(LocalDate dateToCompare) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Compare the dates
        return dateToCompare.equals(currentDate);
    }

    public static boolean isCurrentTimeBetween(LocalTime startTime, LocalTime endTime) {
        LocalTime now = LocalTime.now();

        if (startTime.isBefore(endTime)) {
            // Normal case: startTime < endTime
            return now.isAfter(startTime) && now.isBefore(endTime);
        } else {
            // Overnight case: startTime > endTime (e.g., 10 PM to 6 AM)
            return now.isAfter(startTime) || now.isBefore(endTime);
        }
    }
}
