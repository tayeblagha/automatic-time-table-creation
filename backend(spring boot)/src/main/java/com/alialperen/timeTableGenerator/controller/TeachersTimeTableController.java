package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.dto.DayTimeDto;
import com.alialperen.timeTableGenerator.entity.TeachersTimeTable;
import com.alialperen.timeTableGenerator.service.TeachersTimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.alialperen.timeTableGenerator.entity.DateUtils.getPreviousMondayOrTodayIfMonday;

@RestController
@RequestMapping("/api/teacherstimtable")
@RequiredArgsConstructor
public class TeachersTimeTableController {

    private final TeachersTimeTableService teachersTimeTableService;

    @PostMapping
    public ResponseEntity<TeachersTimeTable> createTimeTable(@RequestBody TeachersTimeTable teachersTimeTable) {
        return ResponseEntity.ok(teachersTimeTableService.saveTeachersTimeTable(teachersTimeTable));
    }

    @GetMapping("/{teacherId}/{startDate}")
    public ResponseEntity<TeachersTimeTable> getTimeTable(@PathVariable Long teacherId, @PathVariable String startDate) {
        Optional<TeachersTimeTable> teachersTimeTable = teachersTimeTableService.getTeachersTimeTableById(teacherId, startDate);
        return teachersTimeTable.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TeachersTimeTable>> getAllTimeTables() {
        return ResponseEntity.ok(teachersTimeTableService.getAllTeachersTimeTables());
    }

    @DeleteMapping("/{teacherId}/{startDate}")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long teacherId, @PathVariable String startDate) {
        teachersTimeTableService.deleteTeachersTimeTable(teacherId, startDate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current/{teacherId}")
    public ResponseEntity<TeachersTimeTable> getCurrentTimeTable(@PathVariable Long teacherId) {
        Optional<TeachersTimeTable> teachersTimeTable = teachersTimeTableService.getCurrentTeachersTimeTableById(teacherId);
        return teachersTimeTable.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/currentslot/{teacherId}")
    public DayTimeDto searchTimetableSlots(@PathVariable Long teacherId){
        return  teachersTimeTableService.searchTimetableSlots(teacherId);
    }
}
