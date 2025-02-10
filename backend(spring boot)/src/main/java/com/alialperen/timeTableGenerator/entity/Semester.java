package com.alialperen.timeTableGenerator.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Semester")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private SemesterNumber number;

    @ManyToMany(mappedBy = "semesters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Major> majors = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SemesterNumber getNumber() {
        return number;
    }

    public void setNumber(SemesterNumber number) {
        this.number = number;
    }

//    public Collection<Major> getMajors() {
//        return majors;
//    }
//
//    public void setMajors(Collection<Major> majors) {
//        this.majors = majors;
//    }
}


