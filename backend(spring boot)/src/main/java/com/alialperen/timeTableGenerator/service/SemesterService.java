package com.alialperen.timeTableGenerator.service;

import java.util.List;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;


public interface SemesterService {
	void createDefaultSemesters();
	public void updateCurrentYear(int year);
	List<Semester> getSemesters();
	public List<Semester> getFirstNSemesters(int n);
	Semester addSemster(Semester semster);
	
	String deleteSemester(long id);
	
	Semester getSemesterById(long id);
	
	Semester updateSemester(long id, Semester semster);
	
	List<Semester> findSemesterByNum(SemesterNumber num);

}
