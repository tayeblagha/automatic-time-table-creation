package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.RoomOrder;
import com.alialperen.timeTableGenerator.repository.ClassroomRepository;
import com.alialperen.timeTableGenerator.repository.RoomOrderRepository;
import com.alialperen.timeTableGenerator.service.RoomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.alialperen.timeTableGenerator.entity.enums.TypeRoom;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomOrderServiceImp implements RoomOrderService  {

    private final RoomOrderRepository roomOrderRepository;
    private final ClassroomRepository classroomRepository;


    @Override
    public List<RoomOrder> findByClassId(Long classId) {
        return  roomOrderRepository.findByClassId(classId);
    }

    @Override
    public List<RoomOrder> updateOrder(List<RoomOrder> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList(); // Handle the case where the input list is null or empty.
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).setOrderIndex(i);
        }

        return roomOrderRepository.saveAll(items);
    }




    @Override
    public void initializeItems(Classes cl) {
       List<Classroom> classrooms = classroomRepository.findByTypeRoom(TypeRoom.Room);
       for ( int i =0; i<classrooms.size();i++){
           RoomOrder ro= new RoomOrder();
           ro.setClasses(cl);
           ro.setClassroom(classrooms.get(i));
           ro.setOrderIndex(i);
           roomOrderRepository.save(ro);
       }
    }

    @Override
    public List<Classroom> findClassroomsByClassId(Long classId) {
        return roomOrderRepository.findClassroomsByClassId(classId);
    }
}
