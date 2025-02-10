
package com.alialperen.timeTableGenerator.service.imp;
import com.alialperen.timeTableGenerator.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import com.alialperen.timeTableGenerator.config.DataFromDb;
//import com.alialperen.timeTableGenerator.entity.Classes;
//import com.alialperen.timeTableGenerator.entity.ModuleElement;
//import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
//import com.alialperen.timeTableGenerator.entity.*;
//import com.alialperen.timeTableGenerator.geneticAlgorithm.GeneticAlgorithm;
//import com.alialperen.timeTableGenerator.geneticAlgorithm.TimeTable;
//import com.alialperen.timeTableGenerator.service.ModuleElementService;
//import com.alialperen.timeTableGenerator.service.ScheduleService;
//import com.alialperen.timeTableGenerator.service.TeacherService;
//
//import lombok.RequiredArgsConstructor;
//
@Service
@RequiredArgsConstructor
public class ScheduleServiceImp implements ScheduleService {
//
//	private final DataFromDb dataFromDb;
//
//	private final ModuleElementService moduleElementService;
//
//	private final TeacherService teacherService;
//
//
//	@Override
//	public List<Map<Long, List<ModuleElement>>> getAllSchedules() {
//		List<Map<Long, List<ModuleElement>>> schedules = new ArrayList<>();
//        dataFromDb.loadDataFromDatabase();
//
//        List<ModuleElement> elements = DataFromDb.moduleElements;
//
//        for(ModuleElement element : elements) {
//        	Map<Long,List<ModuleElement>> schedule = new HashMap<>();
//        	schedule.put(element.getId(),moduleElementService.getSchedulesByClass(element.getId()));
//        	schedules.add(schedule);
//        }
//		return schedules;
//	}
//
//	@Override
//	public List<ModuleElement> getSchedulesByClass(Long id) {
//		// TODO Auto-generated method stub
//		return moduleElementService.getSchedulesByClass(id);
//	}
//
//	@Override
//	public List<Map<Long, List<ModuleElement>>> generateSchedules() {
//		List<Map<Long, List<ModuleElement>>> schedules = new ArrayList<>();
//		dataFromDb.loadDataFromDatabase();
//
//		GeneticAlgorithm ga = new GeneticAlgorithm();
//
//		TimeTable timetable = ga.generateTimetable();
//
//		for(int i = 0; i<timetable.getNumberOfLessons(); i++) {
//			Map<Long, List<ModuleElement>> schedule = new HashMap<>();
//			schedule.put(timetable.getLessons().get(i).getId(), timetable.getTimeTable(i));
//			schedules.add(schedule);
//		}
//
//		for(ModuleElement moduleElement : timetable.getAllElements()) {
//			moduleElementService.addModuleElement(moduleElement);
//
//		}
//
//
//		return schedules;
//	}
//
//	@Override
//	public List<ModuleElement> getScheduleByTeacherAndSemester(Long teacherId, SemesterNumber semesterNumber) throws Exception {
//	    Teacher teacher = teacherService.findTeacherById(teacherId);
//
//	    List<ModuleElement> moduleElements = new ArrayList<>();
//	    for(ModuleElement moduleElement : teacher.getModuleElements()) {
//	        if(moduleElement.getModule().getLesson().getSemester().getNumber() == semesterNumber) {
//	            moduleElements.add(moduleElement);
//	        }
//	    }
//	    return moduleElements;
//	}
//
//	@Override
//	public List<ModuleElement> getScheduleByTeacher(Long id) throws Exception {
//		Teacher teacher = teacherService.findTeacherById(id);
//
//		List<ModuleElement> moduleElements = new ArrayList<>();
//		for(ModuleElement moduleElement : teacher.getModuleElements()) {
//			if(moduleElement.getModule().getLesson().getSemester().getNumber() == SemesterNumber.S1 ||moduleElement.getModule().getLesson().getSemester().getNumber() == SemesterNumber.S2 ) {
//				moduleElements.add(moduleElement);
//			}
//		}
//		return moduleElements;
//	}
//
}
