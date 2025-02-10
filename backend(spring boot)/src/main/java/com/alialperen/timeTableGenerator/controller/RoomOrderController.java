package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.entity.RoomOrder;
import com.alialperen.timeTableGenerator.service.RoomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/roomorders")
@RequiredArgsConstructor
public class RoomOrderController {

    private final RoomOrderService roomOrderService;

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<RoomOrder>> getRoomOrdersByClassId(@PathVariable Long classId) {
        List<RoomOrder> roomOrders = roomOrderService.findByClassId(classId);
        return ResponseEntity.ok(roomOrders);
    }

    // Endpoint to update Room Orders
    @PutMapping("/")
    public ResponseEntity<List<RoomOrder>> updateRoomOrders(@RequestBody List<RoomOrder> roomOrders) {
        return ResponseEntity.ok(roomOrderService.updateOrder(roomOrders)) ;

    }
}
