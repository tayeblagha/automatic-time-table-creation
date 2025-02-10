package com.alialperen.timeTableGenerator.dto;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.Major;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayTimeDto {


    private LocalTime startTime;

    private  LocalTime endTime;

    private Long roomIndex;
    private Classroom room;

    private Classes classes;

    private Long classIndex;

    @JsonIncludeProperties({"name","label","id"})
    private Major major;

    private Long majorIndex;
    public DayTimeDto() {
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(Long classIndex) {
        this.classIndex = classIndex;
    }

    public Long getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(Long roomIndex) {
        this.roomIndex = roomIndex;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Classroom getRoom() {
        return room;
    }

    public void setRoom(Classroom room) {
        this.room = room;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Long getMajorIndex() {
        return majorIndex;
    }

    public void setMajorIndex(Long majorIndex) {
        this.majorIndex = majorIndex;
    }
}
