package com.alialperen.timeTableGenerator.service;

import java.util.List;

import com.alialperen.timeTableGenerator.entity.GradeDuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Semester;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MajorService {
	List<Major> findTheDepartmentIds(List<Long> departmentIds);

	Major createMajor(Major major);
	Page<Major> findByDepartmentIdsAndLabelLike(List<Long> departmentIds, String words, Pageable pageable);
	List<Major> getAllMajors();
	Page<Major> getMajorsByDepartments(List<Long> departmentIds,Pageable pageable);
	String deleteMajor(long id);
	
	Major getMajorById(long id);
	
	Major updateMajor(long id,Major major);
	
	Page<Major> getMajors(Pageable pageable);
	
	Page<Major> searchMajors(String keyword, Pageable pageable);
	
	List<Semester> getSemesterByMajor(long majorId);
	List<Major> getMajorsBySemesterId(int id);
	 Major updateMajorGradeDuration(Long id, List<GradeDuration> gradeDurations);

	List<Major> findByGradeDurationGradeAndDepartmentId(int grade, long departmentId);

	List<Major> findByGradeDurationGradeAndSemesterNumber(int grade,  String semesterNumber);
	List<Major> findByDepartmentId( long departmentId);
	List<Major> findByGradeDurationGradeAndDepartmentIdAndSemesterNumber(int grade, int departmentId, int semesterId);

}
