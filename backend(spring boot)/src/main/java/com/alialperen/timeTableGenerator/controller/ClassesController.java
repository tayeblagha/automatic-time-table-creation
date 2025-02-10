package com.alialperen.timeTableGenerator.controller;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.service.ClassesService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassesController {
	
	private final ClassesService classesService;
	
	
	   @GetMapping
	    public Page<Classes> getAllClasses(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "20") int size
	    ) {
	        Pageable pageable = PageRequest.of(page, size);
	        return classesService.getLessons(pageable);
	    }
	@GetMapping("/all")
	public List<Classes> getAllClasses(){
		return  classesService.getAllLessons();
	}

	    @GetMapping("/{id}")
	    public Classes getClassById(@PathVariable Long id) throws Exception {
	        return classesService.findLessonById(id);
	    }

	    @PostMapping("/{id}")
	    public Classes createClass(@RequestBody Classes lesson,@PathVariable Long id) throws Exception {
	        return classesService.createFullLesson(lesson, id);
	    }

	    @PutMapping("/{id}")
	    public Classes updateClass(@PathVariable Long id, @RequestBody Classes updatedClass) throws Exception {
	        return classesService.updateLesson(id, updatedClass);
	    }

	    @DeleteMapping("{id}")
	    public String deleteClass(@PathVariable Long id) {
	        return classesService.deleteLesson(id);
	    }

	    @GetMapping("/search")
	    public Page<Classes> searchClasses(
	            @RequestParam String keyword,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size
	    ) {
	        Pageable pageable = PageRequest.of(page, size);
	        return classesService.searchLessons(keyword, pageable);
	    }

	@GetMapping("/search/{departmentId}")
	public Page<Classes> searchClassesByLabelAndDepartment(
			@PathVariable Long departmentId,
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		return classesService.searchClassByLabelAndDepartment(keyword,departmentId,pageable);
	}

	    @GetMapping("/searchSem")
	    public Page<Classes> searchClassesSem(
	            @RequestParam String keyword,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam Long sem
	    ) {
	        Pageable pageable = PageRequest.of(page, size);
	        return classesService.searchLessons(keyword,sem, pageable);
	    }


	@GetMapping("/department/{departmentId}/grade/{grade}")
	public List<Classes> getClassesByDepartmentandGrade(
			@PathVariable Long departmentId,
			@PathVariable  int grade
	){
		return  classesService.getClasseByDepartementAndGrade(departmentId,grade);
	}



}
