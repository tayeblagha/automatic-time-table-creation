package com.alialperen.timeTableGenerator.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
	
	List<Semester> findByNumber(SemesterNumber number);
    @Query(value = "SELECT * FROM semester ORDER BY id ASC LIMIT ?1", nativeQuery = true)
    List<Semester> findFirstNSemesters( int n );


}
