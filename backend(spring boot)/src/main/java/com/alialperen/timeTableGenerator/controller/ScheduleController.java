package com.alialperen.timeTableGenerator.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alialperen.timeTableGenerator.service.ScheduleService;
//import com.alialperen.timeTableGenerator.entity.*;
//import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
//
//import lombok.RequiredArgsConstructor;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/schedules")
//@RequiredArgsConstructor
public class ScheduleController {
//
//	private final ScheduleService scheduleService;
//
//	@GetMapping
//	public List<Map<Long,List<ModuleElement>>> getAllSchedules(){
//		return scheduleService.getAllSchedules();
//	}
//
//	@GetMapping("/{id}")
//	public List<ModuleElement> getSchedulerBYClassroom(@PathVariable long id){
//		return scheduleService.getSchedulesByClass(id);
//	}
//
//	@GetMapping("/generate")
//	public List<Map<Long,List<ModuleElement>>> generateSchedule(){
//		return scheduleService.generateSchedules();
//	}
//
//	@GetMapping("/teacher/{id}")
//	public List<ModuleElement> getScheduleByTeacher(@PathVariable long id) throws Exception{
//		return scheduleService.getScheduleByTeacher(id);
//	}
//
//	@GetMapping("/teacher/{id}/semester/{semester}")
//	public List<ModuleElement> getScheduleByTeacherAndSemester(@PathVariable long id, @PathVariable SemesterNumber semester) throws Exception{
//	    return scheduleService.getScheduleByTeacherAndSemester(id, semester);
//	}
}
