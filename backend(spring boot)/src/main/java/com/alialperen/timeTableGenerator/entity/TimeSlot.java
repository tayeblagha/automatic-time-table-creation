package com.alialperen.timeTableGenerator.entity;

import java.time.LocalDate;
import java.time.LocalTime;
public class TimeSlot {
    private LocalDate date; // Added date to handle day-specific scheduling
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Checks if this time slot overlaps with another time slot.
     * Overlaps only occur if they are on the same day and their time ranges intersect.
     */
    public boolean overlaps(TimeSlot other) {
        if (!this.date.equals(other.date)) {
            return false; // Different days, no overlap
        }
        // Check if the time ranges overlap
        return this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}