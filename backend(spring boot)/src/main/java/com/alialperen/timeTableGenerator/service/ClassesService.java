package com.alialperen.timeTableGenerator.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alialperen.timeTableGenerator.entity.Classes;
import org.springframework.data.repository.query.Param;

public interface ClassesService {
	List<Classes> findByDepartmentAndGrade(Long departmentId,  int grade);

	Classes createLesson(Classes lesson) throws Exception;
	 Classes createFullLesson(Classes lesson,Long department_id );
	Classes findLessonById(long lessonId) ;
	
	//Lesson findLessonByName(String name) throws Exception;
	
	public String deleteLesson(long lessonId);
	
	List<Classes> getAllLessons();
	
	public Classes updateLesson(long lessonId,Classes lesson) throws Exception;

	Page<Classes> getLessons(Pageable pageable);
	
	Page<Classes> searchLessons(String keyword, Pageable pageable);
	
	Page<Classes> searchLessons(String keyword,Long sem, Pageable pageable);
	Page<Classes> searchClassByLabelAndDepartment(String keyword,Long departmentId, Pageable pageable);
	List<Classes> getDepartmentIdsByFilter(@Param("departmentIds") List<Long> departmentIds);

	List<Classes> getClasseByDepartementAndGrade(Long departmentId,int grade);
}
