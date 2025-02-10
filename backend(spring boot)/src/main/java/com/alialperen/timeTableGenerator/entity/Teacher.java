package com.alialperen.timeTableGenerator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User implements Serializable {

    @ManyToMany
    @JoinTable(
            name = "teacher_department",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Collection<Department> departments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "teacher_major",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id")
    )
    private Collection<Major> majors = new ArrayList<>();

    @Column
    private int reservedHours;

    @Column
    private int maxHoursPerWeek = 20;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherClassMajor> TeacherClassMajors = new ArrayList<>();

    @Override
    public String toString() {
        return "Teacher{" +
                getFirstName() +
                ", majors=" + majors +
                ", maxHoursPerWeek=" + maxHoursPerWeek +
                '}';
    }

    public Collection<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Collection<Department> departments) {
        this.departments = departments;
    }

    public Collection<Major> getMajors() {
        return majors;
    }

    public void setMajors(Collection<Major> majors) {
        this.majors = majors;
    }

    public int getReservedHours() {
        countReservedHours();
        return reservedHours;
    }

    public void setReservedHours(int reservedHours) {
        this.reservedHours = reservedHours;
    }

    public int getMaxHoursPerWeek() {
        return maxHoursPerWeek;
    }

    public void setMaxHoursPerWeek(int maxHoursPerWeek) {
        this.maxHoursPerWeek = maxHoursPerWeek;
    }

    public List<TeacherClassMajor> getTeacherClassMajors() {
        return TeacherClassMajors;
    }

    public void  countReservedHours(){
        int tempCount=0;
        for ( TeacherClassMajor tcm:TeacherClassMajors){
            tempCount+=tcm.getMajor().getDurationByGrade(tcm.getClasses().getGrade());
        }
        this.reservedHours=tempCount;
    }

    public void setTeacherClassMajors(List<TeacherClassMajor> teacherClassMajors) {

        TeacherClassMajors = teacherClassMajors;
        countReservedHours();
    }
}