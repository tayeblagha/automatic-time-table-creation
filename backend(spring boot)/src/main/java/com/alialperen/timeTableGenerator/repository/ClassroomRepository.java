package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.enums.TypeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alialperen.timeTableGenerator.entity.Classroom;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
	
	//?????
	@Query("SELECT c FROM Classroom c WHERE c.capacity = ?1")
	Page<Classroom> searchWithPagination(int keyword,Pageable pageable);

	@Query("SELECT c FROM Classroom c WHERE CONCAT(c.typeRoom, '-', c.numRoom) LIKE %:label%")
	Page<Classroom> findByLabelContaining(@Param("label") String label, Pageable pageable);


	@Query("SELECT c FROM Classroom c WHERE CONCAT(c.typeRoom) Like %:typeRoom% AND  CONCAT(c.typeRoom, '-', c.numRoom) LIKE %:label%")
	Page<Classroom> findByLabelContaining(@Param("label") String label,@Param("typeRoom") String typeRoom, Pageable pageable);

	List<Classroom> findByTypeRoom(TypeRoom typeRoom);

}
