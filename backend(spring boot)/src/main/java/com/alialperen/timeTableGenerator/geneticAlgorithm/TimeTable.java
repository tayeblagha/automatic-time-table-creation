package com.alialperen.timeTableGenerator.geneticAlgorithm;
//import java.time.DayOfWeek;
//import java.util.*;
//
//import com.alialperen.timeTableGenerator.entity.*;
//import com.alialperen.timeTableGenerator.entity.ModuleElement;
//import com.alialperen.timeTableGenerator.entity.Teacher;
//import com.alialperen.timeTableGenerator.entity.enums.Period;
//import com.alialperen.timeTableGenerator.entity.Module;
public class TimeTable {
//
//	private List<List<ModuleElement>> timeTabels;
//	private int fitness;
//
//    public TimeTable(int numberOfClasses) {
//        this.timeTabels = new ArrayList<>(numberOfClasses);
//        for (int i = 0; i < numberOfClasses; i++) {
//            this.timeTabels.add(new ArrayList<>());
//        }
//    }
//
//    public int getFitness() {
//        return fitness;
//    }
//
//    public void setFitness(int fitness) {
//        this.fitness = fitness;
//    }
//
//
//
////    public boolean hasConflict(int classIndex, DayOfWeek day, Period period) {
////        List<ModuleElement> classSchedule = timeTabels.get(classIndex);
////        for (ModuleElement element : classSchedule) {
////        	 if (element.getDay().equals(day) && element.getPeriod().equals(period)) {
////                return true;
////            }
////        }
////        return false;
////    }
////
//	public List<Teacher> getTeachers(){
//		List<Teacher> teachers = new ArrayList<>();
//		for(List<ModuleElement> timeTable : timeTabels) {
//			for(ModuleElement element : timeTable) {
//				Teacher teacher = element.getTeacher();
//				if(teacher !=null && !teachers.contains(teacher)) {
//					teachers.add(teacher);
//				}
//			}
//		}
//		return teachers;
//	}
//
//	public void addModuleElement(int classIndex,ModuleElement moduleElement) {
//		List<ModuleElement> timetable = timeTabels.get(classIndex);
//		timetable.add(moduleElement);
//	}
//
//	public List<ModuleElement> getTimeTable(int classIndex){
//		return timeTabels.get(classIndex);
//	}
//
//	public int getNumberOfLessons() {
//		return timeTabels.size();
//	}
//
//	public int calculateFitness() {
//		Criteria criterias = new Criteria(this);
//		int unSatisfiedCriterias = 0;
//
//
//		unSatisfiedCriterias += criterias.isTeachersAvailabilitySatisfied();
//
//		unSatisfiedCriterias += criterias.isTeacherClassConflictSatisfied();
//
//		unSatisfiedCriterias += criterias.isLessonSessionsConflictSatisfied();
//
//		unSatisfiedCriterias += criterias.isClassroomOccupancySatisfied();
//
//		//unSatisfiedCriterias += criterias.areElementsAdjacent();
//
////		unSatisfiedCriterias += criterias.areElementsInTheSamePeriod();
//
//		this.fitness = unSatisfiedCriterias;
//
//
//		return this.fitness;
//	}
//
//	public List<Classes> getLessons(){
//		List<Classes> lessons = new ArrayList<>();
//		for(List<ModuleElement> timetable : timeTabels ) {
//			for(ModuleElement element : timetable) {
//				Classes lesson = element.getModule().getLesson();
//				if(lesson !=null && !lessons.contains(lesson)) {
//					lessons.add(lesson);
//				}
//			}
//		}
//		return lessons;
//	}
//
//	public List<Module> getModules(){
//		List<Module> modules = new ArrayList<>();
//		for(List<ModuleElement> timetable : timeTabels) {
//			for(ModuleElement element : timetable) {
//				Module module = element.getModule();
//				if(module != null && !modules.contains(module)) {
//					modules.add(module);
//				}
//			}
//		}
//		return modules;
//	}
//
//	public List<ModuleElement> getAllElements(){
//		List<ModuleElement> elements = new ArrayList<>();
//		for(List<ModuleElement> timetable : timeTabels) {
//			for(ModuleElement element : timetable) {
//				if(!elements.contains(element)) {
//					elements.add(element);
//				}
//			}
//		}
//		return elements;
//	}
//
//	public void swapGenes(int classIndex, int position1, int position2) {
//		List<ModuleElement> timetable = timeTabels.get(classIndex);
//		ModuleElement gene1 = timetable.get(position1);
//		ModuleElement gene2 = timetable.get(position2);
//		timetable.set(position1, gene2);
//		timetable.set(position2, gene1);
//	}
//
//	public void setTimetable(int classIndex,List<ModuleElement> classTimetable) {
//		timeTabels.set(classIndex, classTimetable);
//	}
//
}
