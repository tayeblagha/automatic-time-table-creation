package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.entity.GradeDuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Semester;
import com.alialperen.timeTableGenerator.service.MajorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {
	
	private final MajorService majorService;
	
	
    @GetMapping
    public Page<Major> getAllMajors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return majorService.getMajors(pageable);
    }
    
    @GetMapping("/all")
    public List<Major> getAllMajors(){
    	return majorService.getAllMajors();
    }

    @GetMapping("/grade/{grade_id}/department/{department_id}")
    public List<Major> findByGradeDurationGrade(@PathVariable int grade_id,@PathVariable long department_id ){

        List<Major> majors= majorService.findByGradeDurationGradeAndDepartmentId(grade_id,department_id);

        for (Major m:majors){
            m.getGradeDurations().removeIf(gd -> gd.getGrade() != grade_id);
        }
        return  majors;
    }

    @GetMapping("/department/{department_id}")
    public List<Major> findByDepartmentID(@PathVariable long department_id ){
        return majorService.findByDepartmentId(department_id);
    }
    @PostMapping("/departments")
    public Page<Major> findByDepartments(@RequestBody  List<Long> departmentIds,  @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size ){
        Pageable pageable = PageRequest.of(page, size);
        return majorService.getMajorsByDepartments(departmentIds,pageable);
    }
    @PostMapping("/departmentids")
    public List<Major> findByDepartmentsIds(@RequestBody  List<Long> departmentIds ){
        return majorService.findTheDepartmentIds(departmentIds);
    }

    @PostMapping("/departments/{word}")
    public Page<Major> findByLabelLike(@PathVariable String word,@RequestBody  List<Long> departmentIds,  @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size ){
        Pageable pageable = PageRequest.of(page, size);
        return majorService.findByDepartmentIdsAndLabelLike(departmentIds,word,pageable);
    }


    @GetMapping("/grade/{id}/{s}")
    public List<Major> findByGradeDurationGrade(@PathVariable int id,@PathVariable String s){

        return majorService.findByGradeDurationGradeAndSemesterNumber(id,s);
    }



    @GetMapping("/semester/{id}")
    public List<Major> getAllMajorsBySemeterId(@PathVariable int id){
        return majorService.getMajorsBySemesterId(id);
    }


    @GetMapping("/{id}")
    public Major getMajorById(@PathVariable Long id) {
        return majorService.getMajorById(id);
    }

    @PostMapping
    public Major createMajor(@RequestBody Major major) {
        return majorService.createMajor(major);
    }

    @PutMapping("/{id}")
    public Major updateMajor(@PathVariable Long id, @RequestBody Major updatedMajor) {
        return majorService.updateMajor(id, updatedMajor);
    }

    @PutMapping("/{id}/grade_duration")
    public Major updateMajorGradeDuration(@PathVariable Long id, @RequestBody List<GradeDuration> gradeDurations) {
        return majorService.updateMajorGradeDuration(id,gradeDurations );
    }



    @DeleteMapping("/{id}")
    public String deleteMajor(@PathVariable Long id) {
        return majorService.deleteMajor(id);
    }

    @GetMapping("/search")
    public Page<Major> searchMajors(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return majorService.searchMajors(keyword, pageable);
    }

    @GetMapping("/{id}/semesters")
    public List<Semester> getSemestersByMajors(@PathVariable Long id) {
        return majorService.getSemesterByMajor(id);
    }
}

