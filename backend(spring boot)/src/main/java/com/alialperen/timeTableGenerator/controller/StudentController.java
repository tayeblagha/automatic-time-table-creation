package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Student;
import com.alialperen.timeTableGenerator.service.ClassesService;
import com.alialperen.timeTableGenerator.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ClassesService  classesService;
    @PostMapping("/{class_id}")
    public Student createStudent(@RequestBody Student student,@PathVariable long class_id) {
        Classes c = classesService.findLessonById(class_id);
        student.setClasses(c);
        return studentService.saveStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/search")
    public Page<Student> searchStudents(
            @RequestParam String keyword,
            Pageable pageable) {
        return studentService.searchStudents(keyword, pageable);
    }

    @GetMapping("/class/{classId}")
    public List<Student> findStudentsByClassId(@PathVariable Long classId) {
        return studentService.findStudentsByClassId(classId);
    }

    @GetMapping("/class/count/{classId}")
    public Long  countStudents(@PathVariable Long classId) {
        return studentService.countByClassId(classId);
    }




    @GetMapping("/class/{classId}/search")
    public Page<Student> findStudentsByClassAndKeyword(
            @RequestParam String keyword,
            @PathVariable Long classId,
            Pageable pageable) {
        return studentService.findStudentsByClassAndKeyword(keyword, classId, pageable);
    }

}
