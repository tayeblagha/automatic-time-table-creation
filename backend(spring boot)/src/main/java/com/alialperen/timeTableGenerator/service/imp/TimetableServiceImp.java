package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.dto.TeacherDto;
import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.geneticAlgorithm.TimeTable;
import com.alialperen.timeTableGenerator.repository.*;
import com.alialperen.timeTableGenerator.service.*;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.alialperen.timeTableGenerator.entity.DateUtils.*;

@Service
@RequiredArgsConstructor

public class TimetableServiceImp implements TimetableService {

    private static final LocalTime MORNING_START = LocalTime.of(8, 0);
    private static final LocalTime MORNING_END = LocalTime.of(13, 0);
    private static final LocalTime AFTERNOON_START = LocalTime.of(14, 0);
    private static final LocalTime AFTERNOON_END = LocalTime.of(18, 0);

    private final MajorService majorService;
    private  final RoomOrderService roomOrderService;
    private  final TimetableRepository timetableRepository;
    private final TeachersTimeTableService teachersTimeTableService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassroomRepository roomRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ClassesRepository classRepository ;

    @Autowired
    private TeacherClassMajorRepository teacherClassMajorRepository;



    public List<Timetable> generateWeeklyTimetable(String date) {
        //previous week
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(date, formatter);


        // LocalDate startDate = LocalDate.now().plusDays((8 - LocalDate.now().getDayOfWeek().getValue()) % 7);


        Map<Teacher, Set<DayTime>> teacherAvailability = new HashMap<>();
        Map<Classroom, List<TimeSlot>> roomAvailability = new HashMap<>();

        List<Timetable> timetables = new ArrayList<>();

        List<Classes> classes = classRepository.findAll();
        classes.sort(Comparator.comparingLong((Classes c) -> c.getDepartment().getId()).thenComparingInt(Classes::getGrade));

        for (int z = 0; z < classes.size(); z++) {
            Classes c = classes.get(z);
            List<Major> majors = teacherClassMajorRepository.findMajorsByClassId(c.getId());
            List<Subject> subjects = new ArrayList<>();
            for (Major mj : majors) {
                Subject ts = MajorToSubjectTransformer.transformMajorToSubjects(mj, c.getGrade());
                subjects.add(ts);
            }

            List<Teacher> teachers = teacherClassMajorRepository.findTeachersByClassId(c.getId());
            List<Classroom> rooms = roomOrderService.findClassroomsByClassId(c.getId());
            Map<LocalDate, DaySchedule> timetable = new HashMap<>();

            for (int i = 0; i < 6; i++) {
                LocalDate currentDate = startDate.plusDays(i);
                DaySchedule daySchedule = new DaySchedule(currentDate);
                Set<LocalTime> unavailableTimes = new HashSet<>();
                Map<Subject, Integer> remainingSessions = initializeRemainingSessions(subjects);
                Set<Subject> scheduledSubjects = new HashSet<>();

                Collections.shuffle(teachers);
                Collections.shuffle(subjects);

                for (Subject subject : subjects) {
                    if (!subject.canScheduleMoreSessions() || remainingSessions.get(subject) <= 0 || scheduledSubjects.contains(subject)) {
                        continue;
                    }

                    LocalTime time = findNextAvailableTime(MORNING_START, unavailableTimes, subject);
                    if (time == null || time.isAfter(AFTERNOON_END.minusHours(subject.getHours()))) {
                        continue;
                    }

                    DayTime dayTime = new DayTime(currentDate, time);
                    Teacher teacher = findAvailableTeacher(subject, teachers, teacherAvailability, dayTime, subject.getHours());
                    if (teacher != null) {
                        Classroom room = findAvailableRoom(roomAvailability, rooms, currentDate, time, subject.getHours());
                        if (room != null) {
                            String teacherName = teacher.getFirstName().charAt(0) + "." + teacher.getLastName();
                            teacherName = teacherName.substring(0, Math.min(10, teacherName.length()));
                            TeacherDto teacherDto = new TeacherDto(teacher.getId(), teacherName);
                            daySchedule.addSlot(new ScheduleSlot(time,time.plusHours(subject.getSessionDurations().get(subject.getSessionsScheduled())), subject.getSessionsScheduled(), subject, teacherDto, room));

                            // Update teacher availability
                            for (int j = 0; j < subject.getHours(); j++) {
                                DayTime slotDayTime = new DayTime(currentDate, time.plusHours(j));
                                slotDayTime.setClassIndex(c.getId());
                                slotDayTime.setRoomIndex(room.getId());
                                slotDayTime.setMajorIndex(subject.getId());
                                teacherAvailability.computeIfAbsent(teacher, k -> new HashSet<>()).add(slotDayTime);
                            }

                            markTimeAsUnavailable(time, subject, unavailableTimes);
                            subject.incrementSessionsScheduled();
                            remainingSessions.put(subject, remainingSessions.get(subject) - 1);
                            scheduledSubjects.add(subject);
                        }
                    }
                }

                fillEmptySlots(daySchedule, unavailableTimes, remainingSessions, teachers, rooms, scheduledSubjects, teacherAvailability, roomAvailability, currentDate,c);

                timetable.put(currentDate, daySchedule);
            }
            Timetable t = new Timetable(c, startDate, timetable);
            createTimetable(t);
            timetables.add(t);


        }

        for (Map.Entry<Teacher, Set<DayTime>> entry : teacherAvailability.entrySet()) {
            Teacher teacher = entry.getKey();
            Set<DayTime> availableTimes = entry.getValue();

            TeachersTimeTable  isAvailable = teachersTimeTableService.getTeachersTimeTableByIdOrNull(teacher.getId(),startDate);
            //System.out.println(availableTimes);
            if (isAvailable!=null){
                continue;
            }
            TeachersTimeTable  teachersTimeTable = new TeachersTimeTable();
            teachersTimeTable.setTeacher(teacher);
            teachersTimeTable.setStartDate(startDate);
            List<DayTime>  teacherDaytimes= new ArrayList<DayTime>(availableTimes);
            teacherDaytimes.sort(Comparator.comparing(DayTime::getDate).thenComparing(DayTime::getTime));
            teachersTimeTable.setDayTimes(teacherDaytimes);

            for (DayTime dayTime : teacherDaytimes) {
                dayTime.setTeachersTimeTable(teachersTimeTable);
            }
            teachersTimeTableService.saveTeachersTimeTable(teachersTimeTable);
        }


        return timetables;
    }

    private void fillEmptySlots(DaySchedule daySchedule, Set<LocalTime> unavailableTimes, Map<Subject, Integer> remainingSessions,
                                List<Teacher> teachers, List<Classroom> rooms, Set<Subject> scheduledSubjects,
                                Map<Teacher, Set<DayTime>> teacherAvailability, Map<Classroom, List<TimeSlot>> roomAvailability, LocalDate currentDate,Classes c) {
        for (LocalTime time = MORNING_START; time.isBefore(AFTERNOON_END); time = time.plusHours(1)) {
            if (!unavailableTimes.contains(time)) {
                for (Map.Entry<Subject, Integer> entry : remainingSessions.entrySet()) {
                    Subject subject = entry.getKey();
                    int sessionsLeft = entry.getValue();

                    if (sessionsLeft > 0 && subject.canScheduleMoreSessions() && isWithinAllowedHours(time, subject) && !scheduledSubjects.contains(subject)) {
                        DayTime dayTime = new DayTime(currentDate, time);
                        Teacher teacher = findAvailableTeacher(subject, teachers, teacherAvailability, dayTime, subject.getHours());
                        if (teacher == null) continue;

                        Classroom room = findAvailableRoom(roomAvailability, rooms, currentDate, time, subject.getHours());
                        if (room != null) {
                            String teacherName = teacher.getFirstName().charAt(0) + "." + teacher.getLastName();
                            teacherName = teacherName.substring(0, Math.min(10, teacherName.length()));
                            TeacherDto teacherDto = new TeacherDto(teacher.getId(), teacherName);

                            daySchedule.addSlot(new ScheduleSlot(time,time.plusHours(subject.getSessionDurations().get(subject.getSessionsScheduled())), subject.getSessionsScheduled(), subject, teacherDto, room));

                            // Update teacher availability
                            for (int i = 0; i < subject.getHours(); i++) {
                                DayTime slotDayTime = new DayTime(currentDate, time.plusHours(i));
                                slotDayTime.setClassIndex(c.getId());
                                slotDayTime.setRoomIndex(room.getId());
                                slotDayTime.setMajorIndex(subject.getId());
                                teacherAvailability.computeIfAbsent(teacher, k -> new HashSet<>()).add(slotDayTime);
                            }

                            markTimeAsUnavailable(time, subject, unavailableTimes);
                            subject.incrementSessionsScheduled();
                            remainingSessions.put(subject, sessionsLeft - 1);
                            scheduledSubjects.add(subject);
                            break;
                        }
                    }
                }
            }
        }
    }

    private Teacher findAvailableTeacher(Subject subject, List<Teacher> teachers, Map<Teacher, Set<DayTime>> teacherAvailability,
                                         DayTime startDayTime, int duration) {
        for (Teacher teacher : teachers) {
            List<String> stringSubject = new ArrayList<>();
            for (Major tempMajor : teacher.getMajors()) {
                stringSubject.add(tempMajor.getName());
            }
            if (stringSubject.contains(subject.getName())) {
                boolean isAvailable = true;
                Set<DayTime> busyTimes = teacherAvailability.getOrDefault(teacher, Collections.emptySet());

                for (int i = 0; i < duration; i++) {
                    DayTime checkDayTime = new DayTime(startDayTime.getDate(), startDayTime.getTime().plusHours(i));
                    if (busyTimes.contains(checkDayTime)) {
                        isAvailable = false;
                        break;
                    }
                }

                if (isAvailable) {
                    return teacher;
                }
            }
        }
        return null;
    }

    private Map<Subject, Integer> initializeRemainingSessions(List<Subject> subjects) {
        Map<Subject, Integer> remainingSessions = new HashMap<>();
        for (Subject subject : subjects) {
            remainingSessions.put(subject, subject.getSessionsPerWeek() - subject.getSessionsScheduled());
        }
        return remainingSessions;
    }

    private LocalTime findNextAvailableTime(LocalTime startTime, Set<LocalTime> unavailableTimes, Subject subject) {
        LocalTime time = startTime;
        while (time.isBefore(AFTERNOON_END)) {
            boolean isAvailable = true;

            for (int i = 0; i < subject.getHours(); i++) {
                LocalTime checkTime = time.plusHours(i);

                if (unavailableTimes.contains(checkTime) || !isWithinAllowedHours(checkTime, subject)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                return time;
            }

            time = time.plusHours(1);
        }
        return null;
    }

    private boolean isWithinAllowedHours(LocalTime time, Subject subject) {
        LocalTime endTime = time.plusHours(subject.getHours());
        boolean isInMorning = time.isAfter(MORNING_START.minusMinutes(1)) && endTime.isBefore(MORNING_END.plusMinutes(1));
        boolean isInAfternoon = time.isAfter(AFTERNOON_START.minusMinutes(1)) && endTime.isBefore(AFTERNOON_END.plusMinutes(1));
        boolean doesNotOverlapBreak = !(time.isBefore(LocalTime.of(14, 0)) && endTime.isAfter(LocalTime.of(12, 0)));

        return (isInMorning || isInAfternoon) && doesNotOverlapBreak;
    }

    private void markTimeAsUnavailable(LocalTime time, Subject subject, Set<LocalTime> unavailableTimes) {
        for (int i = 0; i < subject.getHours(); i++) {
            unavailableTimes.add(time.plusHours(i));
        }
    }

    private Classroom findAvailableRoom(Map<Classroom, List<TimeSlot>> roomAvailability,List<Classroom> rooms, LocalDate date, LocalTime startTime, int duration) {
        LocalTime endTime = startTime.plusHours(duration);
        TimeSlot requestedSlot = new TimeSlot(date, startTime, endTime);

        for (Classroom room : rooms) {
            List<TimeSlot> scheduledSlots = roomAvailability.getOrDefault(room, new ArrayList<>());
            boolean isAvailable = true;

            for (TimeSlot slot : scheduledSlots) {
                if (slot.overlaps(requestedSlot)) {
//                    System.out.println("overlaps");
//                    System.out.println(slot);
//                    System.out.println(requestedSlot);
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                scheduledSlots.add(requestedSlot);
                roomAvailability.put(room, scheduledSlots);
                return room;
            }
        }

        return null;
    }



    @Override
    public Timetable createTimetable(Timetable timetable) {
        TimetableId id = new TimetableId(
                timetable.getClassEntity().getId(),
                timetable.getStartDate()
        );

            System.out.println("not exist ");
            return timetableRepository.save(timetable);




    }

    @Override
    public Timetable getTimetableById(TimetableId id) {

        Timetable t= timetableRepository.findById(id).orElse(null);
        //System.out.println(t);
       // System.out.println();
        return  fillTimetable(t);
    }

    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    @Override
    public Timetable updateTimetable(Timetable timetable) {
        TimetableId id = new TimetableId(
                timetable.getClassEntity().getId(),
                timetable.getStartDate()
        );
        if (!timetableRepository.existsById(id)) {
            throw new IllegalArgumentException("Timetable not found for update");
        }
        return timetableRepository.save(timetable);
    }


    @Override
    public void deleteTimetableById(TimetableId id) {
        timetableRepository.deleteById(id);
    }

    public Timetable fillTimetable(Timetable timetable) {
        if (timetable == null) {
            return null;
        }

        for (Map.Entry<LocalDate, DaySchedule> entry : timetable.getScheduleMap().entrySet()) {
            DaySchedule daySchedule = entry.getValue();
            if (daySchedule == null) continue;

            for (ScheduleSlot slot : daySchedule.getSlots()) {
                if (slot == null) continue;

                // Assign Room
                Long roomId = slot.getRoomId();
                slot.setRoom(roomId != null ? roomRepository.findById(roomId).orElse(null) : null);

                // Assign Teacher
                Long teacherId = slot.getTeacherId();
                if (teacherId != null) {
                    Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
                    if (teacher != null) {
                        String teacherName = teacher.getFirstName().charAt(0) + "." + teacher.getLastName();
                        teacherName = teacherName.substring(0, Math.min(10, teacherName.length()));
                        slot.setTeacher(new TeacherDto(teacher.getId(), teacherName));
                    }
                }

                // Assign Subject
                Long subjectId = slot.getSubjectId(); // Assuming this maps to Major
                if (subjectId != null) {
                    Major major = majorRepository.findById(subjectId).orElse(null);
                    if (major != null) {
                        slot.setSubject(MajorToSubjectTransformer.transformMajorToSubjects(
                                major, timetable.getClassEntity().getGrade()
                        ));
                    }
                }
            }
        }

        return timetable;
    }









    @Override
    public List<ScheduleSlot> SearchTimetableSlots(Long ClassId) {

        List<ScheduleSlot>  unordredSlot = new ArrayList<ScheduleSlot>();

        TimetableId id = new TimetableId(ClassId, getPreviousMondayOrTodayIfMonday());
        Timetable  timetable= getTimetableById(id);
        if(timetable==null){return  null;}
        for (Map.Entry<LocalDate, DaySchedule> entry : timetable.getScheduleMap().entrySet()) {
            DaySchedule daySchedule = entry.getValue();
            if (daySchedule == null) continue;
            if (!isDateEqualToCurrentDate(daySchedule.getDate())) continue;

            for (ScheduleSlot slot : daySchedule.getSlots()) {
                if (slot == null) continue;
                LocalTime startTime= slot.getTime();
                if (startTime.isAfter(LocalTime.now() )){
                    unordredSlot.add(slot);
                    continue;
                }

                // Assign Subject
                Long subjectId = slot.getSubjectId(); // Assuming this maps to Major
                if (subjectId != null) {
                    Major major = majorRepository.findById(subjectId).orElse(null);
                    if (major != null) {
                        Subject s= MajorToSubjectTransformer.transformMajorToSubjects(
                                major, timetable.getClassEntity().getGrade()
                        );

                        slot.setSubject(s);
                        int duration=s.getSessionDurations().get(slot.getDurationIndex());
                        LocalTime endTime= startTime.plusHours(duration);
                        if (isCurrentTimeBetween(startTime,endTime)){
                            unordredSlot.add(slot);
                        }

                    }
                }
            }
        }
        unordredSlot.sort(Comparator.comparing(ScheduleSlot::getTime));

        return unordredSlot;
    }

    @Override
    public List<Timetable> findByStartDate(LocalDate startDate) {
        return timetableRepository.findByStartDate(startDate)
                .stream()
                .map(this::fillTimetable)
                .collect(Collectors.toList());
    }



}



