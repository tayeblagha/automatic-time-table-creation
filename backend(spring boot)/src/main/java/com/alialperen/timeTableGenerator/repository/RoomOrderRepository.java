package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {

    @Query("SELECT r FROM RoomOrder r WHERE r.classes.id = :classId ORDER BY r.orderIndex ASC")
    List<RoomOrder> findByClassId(@Param("classId") Long classId);

    @Query("SELECT r.classroom FROM RoomOrder r WHERE r.classes.id = :classId ORDER BY r.orderIndex ASC")
    List<Classroom> findClassroomsByClassId(@Param("classId") Long classId);


}

