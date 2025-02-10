package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.TeachersTimeTable;
import com.alialperen.timeTableGenerator.entity.TeachersTimeTableId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersTimeTableRepository extends JpaRepository<TeachersTimeTable, TeachersTimeTableId> {
}
