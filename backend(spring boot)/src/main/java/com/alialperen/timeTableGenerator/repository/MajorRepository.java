package com.alialperen.timeTableGenerator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alialperen.timeTableGenerator.entity.Major;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long>{

	@Query("SELECT m FROM Major m JOIN m.department d WHERE d.id IN :departmentIds AND (LOWER(m.label) LIKE LOWER(CONCAT('%', :words, '%')) OR LOWER(m.name) LIKE LOWER(CONCAT('%', :words, '%')))")
	Page<Major> findByDepartmentIdsAndLabelLike(List<Long> departmentIds, @Param("words") String words, Pageable pageable);

	@Query("SELECT m FROM Major m WHERE LOWER(m.label) LIKE LOWER(CONCAT('%', :words, '%')) OR LOWER(m.name) LIKE LOWER(CONCAT('%', :words, '%'))")
	Page<Major> findByLabelLike(@Param("words") String words, Pageable pageable);

	@Query("SELECT m FROM Major m JOIN m.department d WHERE d.id=:id AND m.label LIKE %:words%")
	Page<Major> findByDepartmentIdAndLabelLike(@Param("id") int departmentId, @Param("words") String words, Pageable pageable);


	@Query("SELECT m FROM Major m JOIN m.department d WHERE d.id IN :departmentIds")
	List<Major> findTheDepartmentIds(List<Long> departmentIds);


	@Query("SELECT m FROM Major m JOIN m.department d WHERE d.id IN :departmentIds")
	Page<Major> findByDepartmentIds(List<Long> departmentIds, Pageable pageable);

	@Query("SELECT m FROM Major m WHERE m.label LIKE %?1% ")
	Page<Major> searchMajors(String keyword,Pageable pageable);

	@Query("SELECT m FROM Major m JOIN m.semesters s WHERE s.id = ?1")
	List<Major> getMajorsBySemesterId(int id);

	@Query("SELECT m FROM Major m JOIN m.gradeDurations gd JOIN m.department d WHERE gd.grade = ?1 AND d.id = ?2")
	List<Major> findByGradeDurationGradeAndDepartmentId(int grade, long departmentId);

	@Query("SELECT m FROM Major m JOIN m.gradeDurations gd WHERE gd.grade = ?1")
	List<Major> findByGradeDurationGrade( int grade);

	@Query("SELECT m FROM Major m JOIN m.gradeDurations gd JOIN m.semesters s WHERE gd.grade = ?1 AND s.number = ?2")
	List<Major> findByGradeDurationGradeAndSemesterNumber(int grade,  String semesterNumber);

	@Query("SELECT m FROM Major m JOIN m.department d WHERE  d.id = ?1")
	List<Major> findByDepartmentId( long departmentId);

	@Query("SELECT m FROM Major m JOIN m.gradeDurations gd JOIN m.department d JOIN m.semesters s WHERE gd.grade = ?1 AND d.id = ?2 AND s.id = ?3")
	List<Major> findByGradeDurationGradeAndDepartmentIdAndSemesterNumber(int grade, int departmentId, int semesterId);


}
