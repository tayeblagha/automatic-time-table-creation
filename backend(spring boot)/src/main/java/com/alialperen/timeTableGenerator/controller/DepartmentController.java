package com.alialperen.timeTableGenerator.controller;

import java.util.List;

import com.alialperen.timeTableGenerator.entity.Classes;
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

import com.alialperen.timeTableGenerator.entity.Department;
import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	 @GetMapping
	    public List<Department> getAllDepartments() {
	        return departmentService.getDepartments();
	    }

	    @GetMapping("/search")
	    public Page<Department> searchDepartments(
	            @RequestParam(defaultValue = "") String keyword,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "20") int size
	    ) {
	        Pageable pageable = PageRequest.of(page, size);
	        return departmentService.searchDepartments(keyword, pageable);
	    }


	    @GetMapping("/{id}")
	    public Department getDepartmentById(@PathVariable Long id) {
	        return departmentService.getDepartmentById(id);
	    }

	    @PostMapping
	    public Department createDepartment(@RequestBody Department department) {
	        return departmentService.createDepartment(department);
	    }

	    @PutMapping("/{id}")
	    public Department updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
	        return departmentService.updateDepartment(id, updatedDepartment);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteDepartment(@PathVariable Long id) {
	        return departmentService.deleteDepartment(id);
	    }

	    @GetMapping("/{id}/majors")
	    public List<Classes> getClassesByDepartmentId(@PathVariable Long id) {
	        return departmentService.getClassesByDepartmentId(id);
	    }
	}
