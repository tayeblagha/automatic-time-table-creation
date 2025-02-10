package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Majors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String label;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "major_semesters",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id")
    )
    private List<Semester> semesters = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String nameAndDepartment;

    public String getNameAndDepartment() {
        return nameAndDepartment;
    }

    public void setNameAndDepartment(String nameAndDepartment) {
        this.nameAndDepartment = nameAndDepartment;
    }

    @ElementCollection
    @CollectionTable(name = "major_grade_durations", joinColumns = @JoinColumn(name = "major_id"))
    private List<GradeDuration> gradeDurations = new ArrayList<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        this.nameAndDepartment=this.name+"("+this.department.getName()+")";
    }

    public List<GradeDuration> getGradeDurations() {
        return gradeDurations;
    }

    public void setGradeDurations(List<GradeDuration> gradeDurations) {
        this.gradeDurations = gradeDurations;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", name='" + name + '\'' +
                ", gradeDurations=" + gradeDurations +
                '}';
    }

    public int getDurationByGrade(int grade) {
        for (GradeDuration gradeDuration : gradeDurations) {
            if (gradeDuration.getGrade() == grade) {
                return gradeDuration.getDurationPerWeek();
            }
        }
        // Return a default value or throw an exception if the grade is not found
        return 0; // Or throw an exception if preferred
    }

}
