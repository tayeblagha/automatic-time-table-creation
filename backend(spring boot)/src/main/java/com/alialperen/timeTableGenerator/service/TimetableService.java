package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.entity.DaySchedule;
import com.alialperen.timeTableGenerator.entity.ScheduleSlot;
import com.alialperen.timeTableGenerator.entity.Timetable;
import com.alialperen.timeTableGenerator.entity.TimetableId;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TimetableService {
    public List<Timetable> generateWeeklyTimetable(String date);

    Timetable createTimetable(Timetable timetable);

    Timetable getTimetableById(TimetableId id);

    List<Timetable> getAllTimetables();

    Timetable updateTimetable(Timetable timetable);

    void deleteTimetableById(TimetableId id);
    public List<ScheduleSlot> SearchTimetableSlots(Long ClassId);
    List<Timetable> findByStartDate(LocalDate startDate);
}
