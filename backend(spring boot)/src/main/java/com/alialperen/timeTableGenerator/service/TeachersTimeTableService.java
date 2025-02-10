package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.dto.DayTimeDto;
import com.alialperen.timeTableGenerator.entity.TeachersTimeTable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeachersTimeTableService {
    TeachersTimeTable saveTeachersTimeTable(TeachersTimeTable teachersTimeTable);

    Optional<TeachersTimeTable> getTeachersTimeTableById(Long teacherId, String startDate);

    List<TeachersTimeTable> getAllTeachersTimeTables();

    void deleteTeachersTimeTable(Long teacherId, String startDate);
    public TeachersTimeTable getTeachersTimeTableByIdOrNull(Long teacherId, LocalDate startDate);

    Optional<TeachersTimeTable> getCurrentTeachersTimeTableById(Long teacherId);

    public DayTimeDto searchTimetableSlots(Long teacherId);
}
