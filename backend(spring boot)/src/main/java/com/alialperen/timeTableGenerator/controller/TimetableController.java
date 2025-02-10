package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.dto.TeacherDto;
import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.service.ClassroomService;
import com.alialperen.timeTableGenerator.service.MajorService;
import com.alialperen.timeTableGenerator.service.TeacherService;
import com.alialperen.timeTableGenerator.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.alialperen.timeTableGenerator.entity.DateUtils.getPreviousMondayOrTodayIfMonday;

@RestController
@RequestMapping("/api/timetable")

public class TimetableController {
    private final TimetableService timetableService;
    private final ClassroomService classroomService;
    private final TeacherService teacherService;
    private final MajorService majorService;

    public TimetableController(TimetableService timetableService, ClassroomService classroomService, TeacherService teacherService, MajorService majorService) {
        this.timetableService = timetableService;
        this.classroomService = classroomService;
        this.teacherService = teacherService;
        this.majorService = majorService;
    }

    @GetMapping("/generate/{date}")
    public List<Timetable> generateWeeklyTimetable( @PathVariable String date){
        return timetableService.generateWeeklyTimetable(date);
    }


    @PostMapping
    public ResponseEntity<Timetable> createTimetable(@RequestBody Timetable timetable) {
        return ResponseEntity.ok(timetableService.createTimetable(timetable));
    }

    @GetMapping("/{classId}/{startDate}")
    public ResponseEntity<Timetable> getTimetableById(
            @PathVariable Long classId, @PathVariable String startDate) {
        TimetableId id = new TimetableId(classId, LocalDate.parse(startDate));
        return ResponseEntity.ok(timetableService.getTimetableById(id)) ;

    }

    @GetMapping
    public ResponseEntity<List<Timetable>> getAllTimetables() {
        return ResponseEntity.ok(timetableService.getAllTimetables());
    }

    @PutMapping
    public ResponseEntity<Timetable> updateTimetable(@RequestBody Timetable timetable) {
        return ResponseEntity.ok(timetableService.updateTimetable(timetable));
    }

    @DeleteMapping("/{classId}/{startDate}")
    public ResponseEntity<Void> deleteTimetableById(
            @PathVariable Long classId, @PathVariable String startDate) {
        TimetableId id = new TimetableId(classId, LocalDate.parse(startDate));
        timetableService.deleteTimetableById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchslots/{classId}")
    public ResponseEntity<List<ScheduleSlot>> searchslots(
            @PathVariable Long classId) {
        return ResponseEntity.ok(timetableService.SearchTimetableSlots(classId)) ;
    }

    @GetMapping("/searchslot/{classId}/grade/{grade}")
    public ResponseEntity<ScheduleSlot> searchslot(
            @PathVariable Long classId, @PathVariable int grade) throws Exception {
        ScheduleSlot slot = new ScheduleSlot();
        List<ScheduleSlot> slots = timetableService.SearchTimetableSlots(classId) ;
        System.out.println(slots);
        if (!slots.isEmpty()){
            slot=slots.get(0);

            // Assign Room
            Long roomId = slot.getRoomId();
            slot.setRoom(roomId != null ? classroomService.findClassromById(roomId) : null );

            // Assign Teacher
            Long teacherId = slot.getTeacherId();
            if (teacherId != null) {
                Teacher teacher = teacherService.findTeacherById(teacherId);
                if (teacher != null) {
                    String teacherName = teacher.getFirstName().charAt(0) + "." + teacher.getLastName();
                    teacherName = teacherName.substring(0, Math.min(10, teacherName.length()));
                    slot.setTeacher(new TeacherDto(teacher.getId(), teacherName));
                }
            }

            // Assign Subject
            Long subjectId = slot.getSubjectId(); // Assuming this maps to Major
            if (subjectId != null) {
                Major major = majorService.getMajorById(subjectId);
                if (major != null) {
                    slot.setSubject(MajorToSubjectTransformer.transformMajorToSubjects(
                            major, grade
                    ));
                }
            }


        }
        return  ResponseEntity.ok(slot);
    }


    @GetMapping("/current/{classId}")
    public ResponseEntity<List<Timetable>> getCurrentTimetableById(
            @PathVariable Long classId) {
        List<Timetable> timeTables= new ArrayList<>();
        //System.out.println(getPreviousMondayOrTodayIfMonday());
        TimetableId id = new TimetableId(classId, getPreviousMondayOrTodayIfMonday());

        Timetable t = timetableService.getTimetableById(id);

        timeTables.add(t);
        return ResponseEntity.ok(timeTables) ;

    }


    @GetMapping("/search/{startdate}")
    public List<Timetable> getTimetablesByStartDate(@PathVariable String startdate) {
        return timetableService.findByStartDate(LocalDate.parse(startdate));
    }


}
