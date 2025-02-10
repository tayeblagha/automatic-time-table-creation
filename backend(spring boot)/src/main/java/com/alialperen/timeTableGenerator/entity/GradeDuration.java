package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data

@Builder
public class GradeDuration {
    private int grade; // Grade level (e.g., 1, 2, 3, etc.)
    private int durationPerWeek; // Duration per week in hours

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDurationPerWeek() {
        return durationPerWeek;
    }

    public void setDurationPerWeek(int durationPerWeek) {
        this.durationPerWeek = durationPerWeek;
    }

    public GradeDuration(int grade, int durationPerWeek) {
        this.grade = grade;
        this.durationPerWeek = durationPerWeek;
    }

    public GradeDuration() {
    }
}
