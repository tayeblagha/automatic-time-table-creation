package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.Student;
import com.alialperen.timeTableGenerator.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s WHERE   (LOWER(s.firstName) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', ?1, '%'))) OR LOWER(s.email) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Student> searchWithPagination(String keyword, Pageable pageable);

    @Query("SELECT s FROM Student s JOIN s.classes c " +
            "WHERE c.id = :classId " +
            "AND (LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Student> findTeachersByclassesIdAndLabel(
            @Param("keyword") String keyword,
            @Param("classId") Long classId,
            Pageable pageable);

    @Query("SELECT t FROM Student t  WHERE t.classes.id = :classId")
    List<Student> findByClassId(@Param("classId") Long classId);
    @Query("SELECT COUNT(t) FROM Student t WHERE t.classes.id = :classId")
    Long countByClassId(@Param("classId") Long classId);

}


