package com.alialperen.timeTableGenerator.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
@Entity
@Table(name = "day_time")
public class DayTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    private Long roomIndex;
    @Transient
    private Classroom room;

    @Transient
    private Classes classes;

    private Long classIndex;

    @Transient
    @JsonIncludeProperties({"name","label","id"})
    private Major major;

    private Long majorIndex;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "start_date",
                    referencedColumnName = "start_date"  // Refers to the DB column "start_date" in TeachersTimeTable
            ),
            @JoinColumn(
                    name = "teacher_id",
                    referencedColumnName = "teacher_id"  // Refers to the DB column "teacher_id" in TeachersTimeTable
            )
    })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TeachersTimeTable teachersTimeTable;


    public DayTime() {
    }

    public DayTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public DayTime(LocalDate date, LocalTime time, Long roomIndex, Long classIndex,Long majorIndex) {
        this.date = date;
        this.time = time;
        this.roomIndex = roomIndex;
        this.classIndex = classIndex;
        this.majorIndex=majorIndex;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Long getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(Long roomIndex) {
        this.roomIndex = roomIndex;
    }

    public Long getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(Long classIndex) {
        this.classIndex = classIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTime dayTime = (DayTime) o;
        return Objects.equals(id, dayTime.id) &&
                Objects.equals(date, dayTime.date) &&
                Objects.equals(time, dayTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time);
    }

    @Override
    public String toString() {
        return "DayTime{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", roomIndex=" + roomIndex +
                ", classIndex=" + classIndex +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeachersTimeTable getTeachersTimeTable() {
        return teachersTimeTable;
    }

    public void setTeachersTimeTable(TeachersTimeTable teachersTimeTable) {
        this.teachersTimeTable = teachersTimeTable;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
