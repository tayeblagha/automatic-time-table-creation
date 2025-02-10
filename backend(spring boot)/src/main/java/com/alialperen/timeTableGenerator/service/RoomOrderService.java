package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.RoomOrder;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomOrderService {
    List<RoomOrder> findByClassId( Long classId);
    public List<RoomOrder> updateOrder(List<RoomOrder> items) ;
    public void initializeItems(Classes cl) ;
    List<Classroom> findClassroomsByClassId(@Param("classId") Long classId);
}
