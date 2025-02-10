package com.alialperen.timeTableGenerator.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TeachersTimeTableId implements Serializable {
    private LocalDate startDate;
    private Long teacher; // Use the teacher's ID instead of the whole object

    // Default constructor
    public TeachersTimeTableId() {}

    // Constructor
    public TeachersTimeTableId(LocalDate startDate, Long teacher) {
        this.startDate = startDate;
        this.teacher = teacher;
    }

    // Getters and setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachersTimeTableId that = (TeachersTimeTableId) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, teacher);
    }
}
