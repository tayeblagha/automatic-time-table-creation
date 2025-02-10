package com.alialperen.timeTableGenerator.entity;

import com.alialperen.timeTableGenerator.dto.TeacherDto;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class ScheduleSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime time;

    @Column()
    private LocalTime endTime;

    @Column(nullable = false)
    private int durationIndex;

    // Store the IDs of transient fields
    @Column
    private Long subjectId;

    @Column
    private Long teacherId;

    @Column
    private Long roomId;

    @Transient
    private Subject subject;

    @Transient
    private TeacherDto teacher;

    @Transient
    private Classroom room;

    public ScheduleSlot() {
    }

    public ScheduleSlot(LocalTime time, int durationIndex, Subject subject, TeacherDto teacher, Classroom room) {
        this.time = time;
        this.durationIndex = durationIndex;
        this.setSubject(subject);
        this.setTeacher(teacher);
        this.setRoom(room);
    }

    public ScheduleSlot(LocalTime time,LocalTime endTime, int durationIndex, Subject subject, TeacherDto teacher, Classroom room) {
        this.time = time;
        this.endTime=endTime;
        this.durationIndex = durationIndex;
        this.setSubject(subject);
        this.setTeacher(teacher);
        this.setRoom(room);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDurationIndex() {
        return durationIndex;
    }

    public void setDurationIndex(int durationIndex) {
        this.durationIndex = durationIndex;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subjectId = subject != null ? subject.getId() : null; // Assuming Subject has getId()
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
        this.teacherId = teacher != null ? teacher.getId() : null; // Assuming TeacherDto has getId()
    }

    public Classroom getRoom() {
        return room;
    }

    public void setRoom(Classroom room) {
        this.room = room;
        this.roomId = room != null ? room.getId() : null; // Assuming Classroom has getId()
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ScheduleSlot{" +
                "id=" + id +
                ", time=" + time +
                ", durationIndex=" + durationIndex +
                ", subjectId=" + subjectId +
                ", teacherId=" + teacherId +
                ", roomId=" + roomId +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", room=" + room +
                '}';
    }
}
