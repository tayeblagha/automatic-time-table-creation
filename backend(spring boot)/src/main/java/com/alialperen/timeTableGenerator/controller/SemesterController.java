package com.alialperen.timeTableGenerator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alialperen.timeTableGenerator.entity.Semester;
import com.alialperen.timeTableGenerator.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {
	private final SemesterService semesterService;




	 	@GetMapping ("/")
	    public List<Semester> getAllSemestres() {
	        return semesterService.getSemesters();
	    }
		@GetMapping("/{n}")
		public List<Semester> getFirstNSemesters(@PathVariable int n) {
			 return  semesterService.getFirstNSemesters(n);
		}

//	    @GetMapping("/{id}")
//	    public Semester getSemestreById(@PathVariable Long id) {
//	        return semesterService.getSemesterById(id);
//	    }



	    @PostMapping
	    public Semester createSemestre(@RequestBody Semester semestre) {

	        return semesterService.addSemster(semestre);
	    }

	    @PutMapping("/{id}")
	    public Semester updateSemestre(@PathVariable Long id, @RequestBody Semester updatedSemestre) {
	        return semesterService.updateSemester(id, updatedSemestre);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteSemestre(@PathVariable Long id) {
	        return semesterService.deleteSemester(id);
	    }
}
