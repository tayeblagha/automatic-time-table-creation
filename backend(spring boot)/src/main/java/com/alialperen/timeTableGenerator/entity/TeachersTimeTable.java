package com.alialperen.timeTableGenerator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@IdClass(TeachersTimeTableId.class)
public class TeachersTimeTable {
    @Id
    @Column(name = "start_date", nullable = false)  // Explicitly map to "start_date"
    private LocalDate startDate;

    @Id
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)  // Maps to "teacher_id" column
    @JsonIgnoreProperties({"majors","departments","login","password","email","teacherClassMajors"})
    private Teacher teacher;

    @OneToMany(mappedBy = "teachersTimeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayTime> dayTimes = new ArrayList<>();


    // Constructors
    public TeachersTimeTable() {}

    public TeachersTimeTable(LocalDate startDate, Teacher teacher) {
        this.startDate = startDate;
        this.teacher = teacher;
    }

    public TeachersTimeTable(LocalDate startDate, Teacher teacher, List<DayTime> dayTimes) {
        this.startDate = startDate;
        this.teacher = teacher;
        this.dayTimes = dayTimes;
    }

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<DayTime> getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(List<DayTime> dayTimes) {
        this.dayTimes = dayTimes;
    }

}
