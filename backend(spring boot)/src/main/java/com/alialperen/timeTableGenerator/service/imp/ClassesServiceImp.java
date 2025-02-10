package com.alialperen.timeTableGenerator.service.imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alialperen.timeTableGenerator.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.alialperen.timeTableGenerator.repository.ClassroomRepository;
import com.alialperen.timeTableGenerator.repository.ClassesRepository;
import com.alialperen.timeTableGenerator.repository.MajorRepository;
import com.alialperen.timeTableGenerator.service.ClassesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassesServiceImp implements ClassesService {

	private final ClassesRepository lessonRepository;
	
	private final MajorRepository majorRepository;


	@Override
	public List<Classes> findByDepartmentAndGrade(Long departmentId, int grade) {
		return lessonRepository.findByDepartmentAndGrade(departmentId,grade);
	}

	@Override
	public Classes createLesson(Classes lesson){
		return lessonRepository.save(lesson);
	}

	@Override
	public Classes createFullLesson(Classes lesson, Long department_id) {
		Department d = new Department();
		d.setId(department_id);
		lesson.setDepartment(d);
		return lessonRepository.save(lesson);
	}


	@Override
	public Classes findLessonById(long lessonId)  {
		 return lessonRepository.findById(lessonId).orElse(null); }
	@Override
	public String deleteLesson(long lessonId) {
		try {
			findLessonById(lessonId);
            lessonRepository.deleteById(lessonId);
            return "Lesson deleted Successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
	}

	@Override
	public List<Classes> getAllLessons() {
		// TODO Auto-generated method stub
		return lessonRepository.findAll();
	}

	@Override
	public Classes updateLesson(long lessonId, Classes lesson) throws Exception {
		Classes newclass = lessonRepository.findById(lessonId).orElseThrow();
		newclass.setName(lesson.getName());
		newclass.setDepartment(lesson.getDepartment());
		newclass.setLabel(lesson.getLabel());
		newclass.setNumberOfStudents(lesson.getNumberOfStudents());
		newclass.setGrade(lesson.getGrade());
		return lessonRepository.save(newclass);
	}

	@Override
	public Page<Classes> getLessons(Pageable pageable) {
		// TODO Auto-generated method stub
		return lessonRepository.findAll(pageable);
	}

	@Override
	public Page<Classes> searchLessons(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return lessonRepository.searchLessons(keyword, pageable);
	}

	@Override
	public Page<Classes> searchLessons(String keyword, Long sem, Pageable pageable) {
		// TODO Auto-generated method stub
		return lessonRepository.searchLessons(keyword, sem, pageable);
	}

	@Override
	public Page<Classes> searchClassByLabelAndDepartment(String keyword, Long departmentId, Pageable pageable) {
		return lessonRepository.searchClassByLabelAndDepartment(keyword,departmentId,pageable);
	}

	@Override
	public List<Classes> getDepartmentIdsByFilter(List<Long> departmentIds) {
		return  lessonRepository.getDepartmentIdsByFilter(departmentIds);
	}

	@Override
	public List<Classes> getClasseByDepartementAndGrade(Long departmentId, int grade) {
		return  lessonRepository.getClasseByDepartementAndGrade(departmentId,grade);
	}


}
