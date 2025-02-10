package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;


import com.alialperen.timeTableGenerator.entity.TeacherClassMajor;
import com.alialperen.timeTableGenerator.entity.TeacherClassMajorId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherClassMajorRepository extends JpaRepository<TeacherClassMajor, TeacherClassMajorId> {
    // Method to search by classesId
    List<TeacherClassMajor> findByClasses_Id(Long classesId);
    // CRUD operations
    // Custom query to retrieve only teachers by class ID
    @Query("SELECT tcm.teacher FROM TeacherClassMajor tcm WHERE tcm.classes.id = :classesId")
    List<Teacher> findTeachersByClassId(@Param("classesId") Long classesId);

    @Query("SELECT tcm.major FROM TeacherClassMajor tcm WHERE tcm.classes.id = :classesId")
    List<Major> findMajorsByClassId(@Param("classesId") Long classesId);
}
