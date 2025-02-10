package com.alialperen.timeTableGenerator.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.repository.UserRepository;
import com.alialperen.timeTableGenerator.service.TeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherServiceImp implements TeacherService {
	
	private final UserRepository userRepository;
	private  final TeacherRepository teacherRepository;

	@Override
	public Page<Teacher> searchWithPagination(String keyword, Pageable pageable) {
		return teacherRepository.searchWithPagination(keyword,pageable);
	}

	@Override
	public Teacher createTeacher(Teacher teacher) throws Exception {
		// TODO Auto-generated method stub
		return (Teacher) userRepository.save(teacher);
	}

	@Override
	public Teacher findTeacherById(Long teacherId) throws Exception {
		// TODO Auto-generated method stub
		return (Teacher) userRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("User is not exist with " + teacherId + " this id"));
    }

	@Override
	public String deleteTeacher(long teacherId) {
		try {
			findTeacherById(teacherId);
            userRepository.deleteById(teacherId);
            return "Teacher deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
	}

	@Override
	public Page<Teacher> getTeachers(Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findUsersByRole("TEACHER", pageable);
	}

	@Override
	public Teacher updateTeacher(Teacher teacher, long teacherId) throws Exception {
		Teacher newT =  teacherRepository.findById(teacherId).orElseThrow();
		teacher.setId(teacherId);
		return teacherRepository.save(teacher);
	}

	@Override
	public Page<Teacher> searchTeachers(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.searchWithPagination(keyword, pageable);
	}

	@Override
	public List<Teacher> getAllTeachers() {
		// TODO Auto-generated method stub
		return userRepository.findAllByRole("TEACHER");
	}

	@Override
	public List<Teacher> findTeacherByName(String name) {
		// TODO Auto-generated method stub
		return userRepository.findTeacherByName(name);
	}

	@Override
	public Page<Teacher> findTeachersByDepartmentIdAndLabel(String keyword, Long departmentId, Pageable pageable) {
		return teacherRepository.findTeachersByDepartmentIdAndLabel(keyword,departmentId,pageable);
	}

	@Override
	public List<Teacher> findByMajorId(Long majorId) {
		return teacherRepository.findByMajorId(majorId);
	}



}
