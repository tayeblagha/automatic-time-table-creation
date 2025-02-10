package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
public class SchoolGeneralInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;
    @Column
    private  String name;

    @Column
    private  String imageUrl;
    @Column
    private int numberSemesters;


    public int getNumberSemesters() {
        return numberSemesters;
    }

    public void setNumberSemesters(int numberSemesters) {
        this.numberSemesters = numberSemesters;
    }
// Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SchoolGeneralInfo{" +
                "id=" + id +
                ", academicYear=" + academicYear +
                ", name='" + name + '\'' +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
