package com.alialperen.timeTableGenerator.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.alialperen.timeTableGenerator.entity.Teacher;
import org.springframework.data.repository.query.Param;

public interface TeacherService {

	Page<Teacher> searchWithPagination(String keyword, Pageable pageable);

	Teacher createTeacher(Teacher teacher)throws Exception ;
	 
	 Teacher findTeacherById(Long teacherId) throws Exception;
	 
	 public String deleteTeacher(long teacherId);
	 
	 public Page<Teacher> getTeachers(Pageable pageable);
	 
	 public Teacher updateTeacher(Teacher teacher,long teacherId) throws Exception;
	 
	 Page<Teacher> searchTeachers(String keyword,Pageable pageable);
	 
	 List<Teacher> getAllTeachers();
	 
	 List<Teacher> findTeacherByName(String name);

	Page<Teacher> findTeachersByDepartmentIdAndLabel(String keyword,Long departmentId, Pageable pageable);
	 List<Teacher> findByMajorId(Long majorId);
}
