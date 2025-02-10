package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.entity.TeacherClassMajor;
import com.alialperen.timeTableGenerator.entity.TeacherClassMajorId;

import java.util.List;
import java.util.Optional;

public interface TeacherClassMajorService {
    List<TeacherClassMajor> findByClasses_Id(Long classesId);
    List<TeacherClassMajor> findAll();
    Optional<TeacherClassMajor> findById(TeacherClassMajorId id);
    TeacherClassMajor save(TeacherClassMajor teacherClassMajor);
    TeacherClassMajor update(TeacherClassMajorId id,TeacherClassMajor newteacherClassMajor) ;
        void deleteById(TeacherClassMajorId id);
}
