package com.alialperen.timeTableGenerator.controller;

import java.util.List;

import com.alialperen.timeTableGenerator.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.service.ClassroomService;
import com.alialperen.timeTableGenerator.service.ClassesService;
import com.alialperen.timeTableGenerator.service.TeacherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
	
	private final ClassroomService classroomService;


    @GetMapping
    public Page<Classroom> getAllClassrooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return classroomService.getClassrooms(pageable);
    }

    @GetMapping("/{id}")
    public Classroom getClassroomById(@PathVariable Long id) throws Exception {
        return classroomService.findClassromById(id);
    }
    
    @GetMapping("/all")
    public List<Classroom> getAllClassrooms() throws Exception {
        return classroomService.findAllClassrooms();
    }

    @PostMapping
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        return classroomService.createClassroom(classroom);
    }

    @PutMapping("/{id}")
    public Classroom updateClassroom(@PathVariable Long id, @RequestBody Classroom updatedClassroom) {
        return classroomService.updateClassroom(updatedClassroom, id);
    }

    @DeleteMapping("/{id}")
    public String deleteClassroom(@PathVariable Long id) {
        //System.out.println("deleteClassroom"+id);
        return classroomService.deleteClassroom(id);
    }

    @GetMapping("/search")
    public Page<Classroom> searchClassrooms(
            @RequestParam String keyword,
            @RequestParam String typeroom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (keyword.isEmpty() && typeroom.isEmpty()) {
            return getAllClassrooms(page, size); // Return all classrooms if no keyword is provided
        }

        // Search by label (string input)
        return classroomService.findByLabelContaining(keyword,typeroom, pageable);
    }







}
