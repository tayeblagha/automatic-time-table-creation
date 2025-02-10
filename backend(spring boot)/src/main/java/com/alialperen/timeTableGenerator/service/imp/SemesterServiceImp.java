package com.alialperen.timeTableGenerator.service.imp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
import com.alialperen.timeTableGenerator.repository.SemesterRepository;
import com.alialperen.timeTableGenerator.service.SemesterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SemesterServiceImp implements SemesterService {
	
	private final SemesterRepository semesterRepository;
	@Transactional
	public void createDefaultSemesters() {
		int currentYear = LocalDate.now().getYear();

		// Semester 1: January to March
		createSemesterIfNotExists(SemesterNumber.S1,
				LocalDate.of(currentYear, 1, 1),
				LocalDate.of(currentYear, 3, 31));

		// Semester 2: April to June
		createSemesterIfNotExists(SemesterNumber.S2,
				LocalDate.of(currentYear, 4, 1),
				LocalDate.of(currentYear, 6, 30));

		// Semester 3: July to September
		createSemesterIfNotExists(SemesterNumber.S3,
				LocalDate.of(currentYear, 7, 1),
				LocalDate.of(currentYear, 9, 30));

		// Semester 4: October to December
		createSemesterIfNotExists(SemesterNumber.S4,
				LocalDate.of(currentYear, 10, 1),
				LocalDate.of(currentYear, 12, 31));
	}


	private void createSemesterIfNotExists(SemesterNumber number, LocalDate start, LocalDate end) {
		if (semesterRepository.findByNumber(number).isEmpty()) {
			Semester semester = new Semester();
			semester.setNumber(number);
			semester.setStartDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			semester.setEndDate(Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			semesterRepository.save(semester);
		}
	}
	@Override
	public List<Semester> getSemesters() {
		// TODO Auto-generated method stub
		return semesterRepository.findAll();
	}

	public void updateCurrentYear(int year) {
		List<Semester> semesters = semesterRepository.findAll();
		for (Semester semester : semesters) {
			// Update start date
			Date startDate = semester.getStartDate();
			java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
			LocalDate localStartDate = utilStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			localStartDate = localStartDate.withYear(year);
			Date updatedStartDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			semester.setStartDate(updatedStartDate);

			// Update end date
			Date endDate = semester.getEndDate();
			java.util.Date utilEndDate = new java.util.Date(endDate.getTime());
			LocalDate localEndDate = utilEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			localEndDate = localEndDate.withYear(year);
			Date updatedEndDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			semester.setEndDate(updatedEndDate);

			// Save the updated semester
			semesterRepository.save(semester);
		}
	}



	public List<Semester> getFirstNSemesters(int n) {
		return semesterRepository.findFirstNSemesters(n);
	}

	@Override
	public Semester addSemster(Semester semster) {
		// TODO Auto-generated method stub
		return semesterRepository.save(semster);
	}

	@Override
	public String deleteSemester(long id) {
		try {
			getSemesterById(id);
			semesterRepository.deleteById(id);
			return "Deleted succesfully";
		}catch(Exception e) {
			return e.getMessage();
		}
		
	}

	@Override
	public Semester getSemesterById(long id) {
		return semesterRepository.findById(id).orElseThrow(() -> new RuntimeException("This semester is not exist"));
	}

	@Override
	public Semester updateSemester(long id, Semester semster) {
		semster.setId(id);
		
		return semesterRepository.save(semster);
	}

	@Override
	public List<Semester> findSemesterByNum(SemesterNumber num) {
		// TODO Auto-generated method stub
		return semesterRepository.findByNumber(num);
	}

}
