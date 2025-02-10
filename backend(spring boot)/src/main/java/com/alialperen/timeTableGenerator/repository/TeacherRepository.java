package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT e FROM Teacher e WHERE   (LOWER(e.firstName) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', ?1, '%'))) OR LOWER(e.email) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Teacher> searchWithPagination(String keyword, Pageable pageable);

    @Query("SELECT e FROM Teacher e JOIN e.departments d " +
            "WHERE d.id = :departmentId " +
            "AND (LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Teacher> findTeachersByDepartmentIdAndLabel(
            @Param("keyword") String keyword,
            @Param("departmentId") Long departmentId,
            Pageable pageable);

    @Query("SELECT t FROM Teacher t JOIN t.majors m WHERE m.id = :majorId")
    List<Teacher> findByMajorId(@Param("majorId") Long majorId);



}