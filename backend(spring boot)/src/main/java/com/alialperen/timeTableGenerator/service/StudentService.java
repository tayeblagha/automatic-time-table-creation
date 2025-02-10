package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Long countByClassId(@Param("classId") Long classId);
    Student saveStudent(Student student);

    Optional<Student> getStudentById(Long id);

    Student updateStudent(Long id, Student updatedStudent);

    void deleteStudent(Long id);

    List<Student> getAllStudents();

    Page<Student> searchStudents(String keyword, Pageable pageable);

    List<Student> findStudentsByClassId(Long classId);

    Page<Student> findStudentsByClassAndKeyword(String keyword, Long classId, Pageable pageable);

}
