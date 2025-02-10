package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.entity.TeacherClassMajor;
import com.alialperen.timeTableGenerator.entity.TeacherClassMajorId;
import com.alialperen.timeTableGenerator.repository.TeacherClassMajorRepository;
import com.alialperen.timeTableGenerator.service.TeacherClassMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherClassMajorServiceImp implements TeacherClassMajorService {
    private final TeacherClassMajorRepository teacherClassMajorRepository;
    @Override
    public List<TeacherClassMajor> findByClasses_Id(Long classesId) {
        return teacherClassMajorRepository.findByClasses_Id(classesId);
    }

    @Override
    public List<TeacherClassMajor> findAll() {
        return teacherClassMajorRepository.findAll();
    }

    @Override
    public Optional<TeacherClassMajor> findById(TeacherClassMajorId id) {
        return teacherClassMajorRepository.findById(id);
    }

    @Override
    public TeacherClassMajor save(TeacherClassMajor teacherClassMajor) {
        return teacherClassMajorRepository.save(teacherClassMajor);
    }

    @Override
    public TeacherClassMajor update(TeacherClassMajorId id,TeacherClassMajor newteacherClassMajor) {
        TeacherClassMajor   oldTCM= teacherClassMajorRepository.findById(id).orElseThrow();
        oldTCM.setTeacher(newteacherClassMajor.getTeacher());
        return  teacherClassMajorRepository.save(oldTCM);
    }

    @Override
    public void deleteById(TeacherClassMajorId id) {
        if (teacherClassMajorRepository.existsById(id)) {
            teacherClassMajorRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Entity not found for deletion.");
        }
    }
}
