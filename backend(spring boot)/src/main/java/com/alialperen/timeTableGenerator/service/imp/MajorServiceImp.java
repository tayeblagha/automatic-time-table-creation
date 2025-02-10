package com.alialperen.timeTableGenerator.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.alialperen.timeTableGenerator.entity.GradeDuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Semester;
import com.alialperen.timeTableGenerator.repository.MajorRepository;
import com.alialperen.timeTableGenerator.service.MajorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MajorServiceImp implements MajorService{
	
	private final MajorRepository majorRepository;


	@Override
	public List<Major> findTheDepartmentIds(List<Long> departmentIds) {
		return majorRepository.findTheDepartmentIds(departmentIds);
	}

	@Override
	public Major createMajor(Major major) {
		return majorRepository.save(major);
	}

	@Override
	public List<Major> getAllMajors() {
		// TODO Auto-generated method stub
		return majorRepository.findAll();
	}

	@Override
	public Page<Major> getMajorsByDepartments(List<Long> departmentIds,Pageable pageable) {
	return  majorRepository.findByDepartmentIds(departmentIds, pageable);
	}
	@Override
	public Page<Major> findByDepartmentIdsAndLabelLike(List<Long> departmentIds, String words, Pageable pageable){
		if (departmentIds.isEmpty()){
			return  majorRepository.findByLabelLike(words,pageable);
		}
		return  majorRepository.findByDepartmentIdsAndLabelLike(departmentIds,words,pageable);
	}



	@Override
	public String deleteMajor(long id) {
		 try {
			 getMajorById(id);
			 majorRepository.deleteById(id);
	            return "Major deleted successfully";
	        } catch (Exception e) {
	            return e.getMessage();
	        }
	}

	@Override
	public Major getMajorById(long id) {
		// TODO Auto-generated method stub
		 return majorRepository.findById(id).orElseThrow(() -> new RuntimeException("Major is not exist"));
    }

	@Override
	public Major updateMajor(long id, Major updatedMajor) {
		Major mj = majorRepository.findById(id).orElseThrow();
		mj.setLabel(updatedMajor.getLabel());
		mj.setSemesters(updatedMajor.getSemesters());
		mj.setName(updatedMajor.getName());
		mj.setDepartment(updatedMajor.getDepartment());
		return majorRepository.save(mj);
	}

	@Override
	public Page<Major> getMajors(Pageable pageable) {
		// TODO Auto-generated method stub
		return majorRepository.findAll(pageable);
	}

	@Override
	public Page<Major> searchMajors(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return majorRepository.searchMajors(keyword, pageable);
	}

	@Override
	public List<Semester> getSemesterByMajor(long majorId) {
		Major major = getMajorById(majorId);
		List<Semester> semesters = new ArrayList<>();

		
		return semesters;
	}

	@Override
	public List<Major> getMajorsBySemesterId(int id) {
		return majorRepository.getMajorsBySemesterId(id);
	}

	@Override
	public Major updateMajorGradeDuration(Long id, List<GradeDuration> gradeDurations) {
		Major m = majorRepository.findById(id).orElseThrow();
		m.setGradeDurations(gradeDurations);
		return majorRepository.save(m);
	}

	@Override
	public List<Major> findByGradeDurationGradeAndDepartmentId(int grade, long departmentId)
	{
		return majorRepository.findByGradeDurationGradeAndDepartmentId(grade,departmentId);
	}

	@Override
	public List<Major> findByGradeDurationGradeAndSemesterNumber(int grade, String semesterNumber) {
		return majorRepository.findByGradeDurationGradeAndSemesterNumber(grade,semesterNumber);
	}

	@Override
	public List<Major> findByDepartmentId(long departmentId) {
		return majorRepository.findByDepartmentId(departmentId);
	}

	@Override
	public List<Major> findByGradeDurationGradeAndDepartmentIdAndSemesterNumber(int grade, int departmentId, int semesterId) {
		return  majorRepository.findByGradeDurationGradeAndDepartmentIdAndSemesterNumber(grade,departmentId,semesterId);
	}


}
