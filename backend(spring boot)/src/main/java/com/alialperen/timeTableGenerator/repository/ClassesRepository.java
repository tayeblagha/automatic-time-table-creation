package com.alialperen.timeTableGenerator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alialperen.timeTableGenerator.entity.Classes;
import org.springframework.data.repository.query.Param;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

	@Query("SELECT c FROM Classes c WHERE c.department.id = ?1 AND c.grade = ?2 ")
	List<Classes> findByDepartmentAndGrade(Long departmentId, int grade);

	@Query("select e from Classes e where e.department.id in :departmentIds")
	List<Classes> getDepartmentIdsByFilter(@Param("departmentIds") List<Long> departmentIds);

	@Query("select e from Classes e where e.department.id = ?1")
	List<Classes> getClasseByDepartement(Long departmentId);

	@Query("select e from Classes e where e.id = ?1")
	Classes getLessonByID(Long Id);

	@Query("select e from Classes e where e.label LIKE %?1%   ")
	Page<Classes> searchLessons(String keyword, Long sem, Pageable pageable);

	@Query("SELECT e FROM Classes e WHERE LOWER(e.label) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(e.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY e.grade ASC")
	Page<Classes> searchLessons(String keyword, Pageable pageable);

	@Query("SELECT c FROM Classes c WHERE (LOWER(c.label) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%'))) AND c.department.id = ?2 ORDER BY c.grade ASC")
	Page<Classes> searchClassByLabelAndDepartment(String keyword, Long departmentId, Pageable pageable);


	@Query("SELECT c FROM Classes c WHERE (LOWER(c.label) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%'))) AND c.department.id = ?2 ORDER BY c.grade ASC")
	Page<Classes> searchClassByLabelAndDepartmentAndTypeRoom(String keyword, Long departmentId, Pageable pageable);


	@Query("select e from Classes e where e.department.id = ?1 and e.grade= ?2")
	List<Classes> getClasseByDepartementAndGrade(Long departmentId,int grade);
}
