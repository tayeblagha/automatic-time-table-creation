package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.Timetable;
import com.alialperen.timeTableGenerator.entity.TimetableId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, TimetableId> {

    List<Timetable> findByStartDate(LocalDate startDate);

}
