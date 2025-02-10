package com.alialperen.timeTableGenerator.entity;


import java.time.DayOfWeek;

import com.alialperen.timeTableGenerator.entity.enums.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="ModuleElement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private long id;
    
    @Column(name="label")
    private String label;
    
    @Enumerated(EnumType.STRING)
    @Column(name="day")
    private DayOfWeek day;
    
        
    private int peopleTakingCourse;
    
    @Enumerated(EnumType.STRING)
    @Column(name="period")
    private Period period;
    
    @JoinColumn(name="classroom")
    @ManyToOne
    private Classroom classroom;
    
    @JoinColumn(name="module")
    @ManyToOne
    private Module module;
    
    @JoinColumn(name="teacher")
    @ManyToOne
    private Teacher teacher;
	
}
