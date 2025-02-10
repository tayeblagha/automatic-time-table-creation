package com.alialperen.timeTableGenerator.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.enums.Period;

public interface ModuleElementRepository extends JpaRepository<ModuleElement, Long>{
	
	@Query("SELECT e FROM ModuleElement e WHERE e.module.lesson.id = ?1")
	List<ModuleElement> getSchedulesByClass(Long lessonId);
	
	@Query("SELECT e FROM ModuleElement e WHERE e.day = :dayOfWeek AND e.period = :period")
	ModuleElement findByDayOfWeekAndPeriod(@Param("dayOfWeek") DayOfWeek dayOfWeek,@Param("period") Period period);

	
	@Query("SELECT e FROM ModuleElement e WHERE e.day = :dayOfWeek AND e.period = :period AND e.module.lesson.id = :lessonId")
	List<ModuleElement> findByDayOfWeekAndPeriodAndLesson(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("period") Period period, @Param("lessonId") Long lessonId);


	 
	@Query("SELECT e FROM ModuleElement e WHERE e.day = :dayOfWeek AND e.period = :period AND e.teacher.id = :teacherId")
	List<ModuleElement> findByDayOfWeekAndPeriodAndTeacher(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("period") Period period, @Param("teacherId") Long teacherId);

	
	@Query("SELECT e FROM ModuleElement e WHERE e.day = :dayOfWeek AND e.period = :period AND e.module.lesson.id = :lessonId AND e.teacher.id = :teacherId")
	List<ModuleElement> findByDayOfWeekAndPeriodAndLessonAndTeacher(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("period") Period period, @Param("lessonId") Long lessonId,@Param("teacherId") Long teacherId);

}
