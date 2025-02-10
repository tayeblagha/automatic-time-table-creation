package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.dto.DayTimeDto;
import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.repository.ClassesRepository;
import com.alialperen.timeTableGenerator.repository.ClassroomRepository;
import com.alialperen.timeTableGenerator.repository.MajorRepository;
import com.alialperen.timeTableGenerator.repository.TeachersTimeTableRepository;
import com.alialperen.timeTableGenerator.service.TeachersTimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.alialperen.timeTableGenerator.entity.DateUtils.*;

@Service
@RequiredArgsConstructor
public class TeachersTimeTableServiceImp implements TeachersTimeTableService {
    private final TeachersTimeTableRepository teachersTimeTableRepository;
    private final ClassesRepository classesRepository;
    private final ClassroomRepository classroomRepository;
    private final MajorRepository majorRepository;



    @Override
    public TeachersTimeTable getTeachersTimeTableByIdOrNull(Long teacherId, LocalDate startDate) {
        TeachersTimeTableId id = new TeachersTimeTableId(startDate, teacherId);
        TeachersTimeTable ttt= teachersTimeTableRepository.findById(id).orElse(null);
        if (ttt==null){return ttt;}
        for (DayTime d: ttt.getDayTimes()){
            Classes c = classesRepository.findById(d.getClassIndex()).orElse(null);
            Classroom room= classroomRepository.findById(d.getRoomIndex()).orElse(null);
            d.setRoom(room);
            d.setClasses(c);
        }
      return ttt;
    }

    @Override
    public Optional<TeachersTimeTable> getCurrentTeachersTimeTableById(Long teacherId) {
        TeachersTimeTableId id = new TeachersTimeTableId(getPreviousMondayOrTodayIfMonday(), teacherId);
        Optional<TeachersTimeTable> optionalTtt = teachersTimeTableRepository.findById(id);

        optionalTtt.ifPresent(ttt -> {
            for (DayTime d : ttt.getDayTimes()) {
                Classes c = classesRepository.findById(d.getClassIndex()).orElse(null);
                Classroom room = classroomRepository.findById(d.getRoomIndex()).orElse(null);
                Major m = majorRepository.findById(d.getMajorIndex()).orElse(null) ;
                d.setRoom(room);
                d.setClasses(c);
                d.setMajor(m);

            }
        });
        return optionalTtt;
    }

    @Override
    public Optional<TeachersTimeTable> getTeachersTimeTableById(Long teacherId, String startDate) {
        TeachersTimeTableId id = new TeachersTimeTableId(LocalDate.parse(startDate), teacherId);
        Optional<TeachersTimeTable> optionalTtt = teachersTimeTableRepository.findById(id);

        optionalTtt.ifPresent(ttt -> {
            for (DayTime d : ttt.getDayTimes()) {
                Classes c = classesRepository.findById(d.getClassIndex()).orElse(null);
                Classroom room = classroomRepository.findById(d.getRoomIndex()).orElse(null);
                Major m = majorRepository.findById(d.getMajorIndex()).orElse(null) ;
                d.setRoom(room);
                d.setClasses(c);
                d.setMajor(m);
            }
        });

        return optionalTtt;
    }

    @Override
    public List<TeachersTimeTable> getAllTeachersTimeTables() {
        List<TeachersTimeTable> tttList = teachersTimeTableRepository.findAll();

        for (TeachersTimeTable ttt : tttList) {
            for (DayTime d : ttt.getDayTimes()) {
                Classes c = classesRepository.findById(d.getClassIndex()).orElse(null);
                Classroom room = classroomRepository.findById(d.getRoomIndex()).orElse(null);
                Major m = majorRepository.findById(d.getMajorIndex()).orElse(null) ;
                d.setRoom(room);
                d.setClasses(c);
                d.setMajor(m);
            }
        }

        return tttList;
    }


    @Override
    public void deleteTeachersTimeTable(Long teacherId, String startDate) {
        TeachersTimeTableId id = new TeachersTimeTableId(LocalDate.parse(startDate), teacherId);
        teachersTimeTableRepository.deleteById(id);
    }

    @Override
    public TeachersTimeTable saveTeachersTimeTable(TeachersTimeTable teachersTimeTable) {
        //System.out.println("test");
        //System.out.println(teachersTimeTable.getDayTimes());
        return teachersTimeTableRepository.save(teachersTimeTable);
    }

    @Override
    public DayTimeDto searchTimetableSlots(Long teacherId) {
        DayTimeDto dto= new DayTimeDto();
        List<DayTime> unorderedSlots = new ArrayList<>();
        TeachersTimeTableId id = new TeachersTimeTableId(getPreviousMondayOrTodayIfMonday(), teacherId);
        TeachersTimeTable timetable = teachersTimeTableRepository.findById(id).orElse(null);

        if (timetable == null) {
            return null;
        }

        List<DayTime> dayTimes = timetable.getDayTimes().stream()
                .filter(d -> d != null && isDateEqualToCurrentDate(d.getDate()))
                .toList();

        for (DayTime dayTime : dayTimes) {
            if (unorderedSlots.size() > 1) {
                break;
            }
            if (dayTime == null) {
                continue;
            }

            LocalTime startTime = dayTime.getTime();
            if (startTime.isAfter(LocalTime.now())) {
                unorderedSlots.add(dayTime);
                continue;
            }

            LocalTime endTime = startTime.plusHours(1);
            if (isCurrentTimeBetween(startTime, endTime)) {
                unorderedSlots.add(dayTime);
            }
        }

        if (unorderedSlots.isEmpty()) {
            return new DayTimeDto();
        }
        DayTime d= unorderedSlots.get(0);
        dto.setStartTime(d.getTime());
        Classes c = classesRepository.findById(d.getClassIndex()).orElse(null);
        Classroom room = classroomRepository.findById(d.getRoomIndex()).orElse(null);
        Major m = majorRepository.findById(d.getMajorIndex()).orElse(null) ;
        dto.setRoom(room);
        dto.setClasses(c);
        dto.setMajor(m);
        dto.setEndTime(d.getTime().plusHours(1));
        if (unorderedSlots.size() > 1 && !Objects.equals(unorderedSlots.get(0).getClassIndex(), unorderedSlots.get(1).getClassIndex())) {
            dto.setEndTime(d.getTime().plusHours(2));
               }

      return   dto;
    }


}




