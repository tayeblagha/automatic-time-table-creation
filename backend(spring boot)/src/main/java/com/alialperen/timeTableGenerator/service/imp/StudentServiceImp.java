package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.entity.Student;
import com.alialperen.timeTableGenerator.repository.StudentRepository;
import com.alialperen.timeTableGenerator.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {



    private final StudentRepository studentRepository;

    @Override
    public Long countByClassId(Long classId) {
        return studentRepository.countByClassId(classId);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setProfilePicture(updatedStudent.getProfilePicture());
            student.setClasses(updatedStudent.getClasses());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Page<Student> searchStudents(String keyword, Pageable pageable) {
        return studentRepository.searchWithPagination(keyword, pageable);
    }

    @Override
    public List<Student> findStudentsByClassId(Long classId) {
        return studentRepository.findByClassId(classId);
    }

    @Override
    public Page<Student> findStudentsByClassAndKeyword(String keyword, Long classId, Pageable pageable) {
        return studentRepository.findTeachersByclassesIdAndLabel(keyword, classId, pageable);
    }
}
