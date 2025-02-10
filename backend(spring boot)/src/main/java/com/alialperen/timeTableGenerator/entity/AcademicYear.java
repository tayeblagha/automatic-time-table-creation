package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class AcademicYear implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment the id

    private Long id; // Unique identifier for each AcademicYear entity
    @Column
    private int startYear;
    @Column
    private int endYear;

    public AcademicYear() {
    }
    public AcademicYear(int startYear){
        this.startYear=startYear;
        this.endYear=this.startYear+1;
    }



    // Getter for startYear
    public int getStartYear() {
        return startYear;
    }

    // Getter for endYear
    public int getEndYear() {
        return endYear;
    }

    // Getter and setter for id (required for @Id)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
        this.endYear=startYear+1;  }

    @Override
    public String toString() {
        return "AcademicYear{" +
                "startYear=" + startYear +
                ", endYear=" + endYear +
                '}';
    }
}
