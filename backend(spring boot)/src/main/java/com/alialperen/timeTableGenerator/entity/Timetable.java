package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@IdClass(TimetableId.class)
public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classes classEntity;

    @Id
    @Column(nullable = false)
    private LocalDate startDate;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "date")
    private Map<LocalDate, DaySchedule> scheduleMap = new HashMap<>();

    public Timetable(Classes classEntity, LocalDate startDate, Map<LocalDate, DaySchedule> scheduleMap) {
        this.classEntity = classEntity;
        this.startDate = startDate;
        this.scheduleMap = scheduleMap;
    }
    public Timetable() {
    }



    // Additional getters and setters can be omitted due to Lombok
}
