package com.alialperen.timeTableGenerator.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TimetableId implements Serializable {

    private Long classEntity;
    private LocalDate startDate;

    public TimetableId() {
    }

    public TimetableId(Long classEntity, LocalDate startDate) {
        this.classEntity = classEntity;
        this.startDate = startDate;
    }

    // Getters and setters
    public Long getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(Long classEntity) {
        this.classEntity = classEntity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimetableId that = (TimetableId) o;
        return Objects.equals(classEntity, that.classEntity) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classEntity, startDate);
    }
}
