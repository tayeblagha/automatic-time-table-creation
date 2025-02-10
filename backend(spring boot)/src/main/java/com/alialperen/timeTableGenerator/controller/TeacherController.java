package com.alialperen.timeTableGenerator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.alialperen.timeTableGenerator.entity.TeacherClassMajor;
import com.alialperen.timeTableGenerator.entity.TeacherClassMajorId;
import com.alialperen.timeTableGenerator.service.TeacherClassMajorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.service.TeacherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
	
	private final TeacherService teacherService;
    private final TeacherClassMajorService teacherClassMajorService;
	@GetMapping
    public Page<Teacher> getAllTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return teacherService.getTeachers(pageable);
    }



    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) throws Exception {
        return teacherService.findTeacherById(id);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) throws Exception {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) throws Exception {
        return teacherService.updateTeacher(updatedTeacher, id);
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }
    @GetMapping("/search")
    public Page<Teacher> searchTeachers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return teacherService.searchWithPagination(keyword, pageable);
    }

    @GetMapping("/search/department/{department_id}")
    public Page<Teacher> searchTeachersByDepartmentAndLabel(
            @PathVariable Long department_id,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return teacherService.findTeachersByDepartmentIdAndLabel(keyword,department_id,pageable);
    }

    @GetMapping("/major/{major_id}")
    List<Teacher> findByMajorId(@PathVariable Long major_id){
        return  teacherService.findByMajorId(major_id);
    }

    @GetMapping("/tcm/class/{class_id}")

    List<TeacherClassMajor> findTCMByClasses_Id(@PathVariable  Long class_id){
        return  teacherClassMajorService.findByClasses_Id(class_id);
    }

    @GetMapping("/tcm/hashclass/{class_id}")
    public Map<Long, Teacher> findHashTcmByClassId(@PathVariable Long class_id) {
        List<TeacherClassMajor> tcmList = teacherClassMajorService.findByClasses_Id(class_id);

        // Transform the list into a HashMap
        Map<Long, Teacher> hashTcm = new HashMap<>();
        for (TeacherClassMajor tcm : tcmList) {
            hashTcm.put(tcm.getMajor().getId(), tcm.getTeacher());
        }

        return hashTcm;
    }


    // Get all
    @GetMapping("/tcm")
    public List<TeacherClassMajor> findAllTCM() {
        return teacherClassMajorService.findAll();
    }

    // Get by ID
    @GetMapping("/tcm/{classesId}/{majorId}")
    public ResponseEntity<TeacherClassMajor> findTCMById(
            @PathVariable Long classesId,
            @PathVariable Long majorId) {
        TeacherClassMajorId id = new TeacherClassMajorId(classesId, majorId);
        Optional<TeacherClassMajor> result = teacherClassMajorService.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create
    @PostMapping("/tcm")
    public ResponseEntity<TeacherClassMajor> createTCM(@RequestBody TeacherClassMajor teacherClassMajor) {
        TeacherClassMajor saved = teacherClassMajorService.save(teacherClassMajor);
        return ResponseEntity.ok(saved);
    }

    // Update
    @PutMapping("/tcm")
    public ResponseEntity<TeacherClassMajor> update(
            @RequestBody TeacherClassMajor teacherClassMajor,
            @RequestParam(defaultValue = "0") long majorid,
            @RequestParam(defaultValue = "0") long classid
            ) {
        try {
            TeacherClassMajorId newTcm=  new TeacherClassMajorId(classid,majorid);
            TeacherClassMajor updated = teacherClassMajorService.update(newTcm,teacherClassMajor);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/tcm/{classesId}/{majorId}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long classesId,
            @PathVariable Long majorId) {
        TeacherClassMajorId id = new TeacherClassMajorId(classesId, majorId);
        try {
            teacherClassMajorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
