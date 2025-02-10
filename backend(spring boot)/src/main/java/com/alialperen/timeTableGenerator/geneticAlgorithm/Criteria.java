package com.alialperen.timeTableGenerator.geneticAlgorithm;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alialperen.timeTableGenerator.entity.ModuleElement;
import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.Module;

public class Criteria {
//
//	TimeTable schoolTimetable;
//
//    public Criteria(TimeTable schoolTimetable) {
//        this.schoolTimetable = schoolTimetable;
//    }
//
//	public int isTeachersAvailabilitySatisfied() {
//		int counter = 0;
//		List<Teacher> teachers = schoolTimetable.getTeachers();
//		for (Teacher teacher : teachers) {
//			for (ModuleElement element : teacher.getModuleElements()) {
//				boolean isAvailable = teacher.getNotAvailables().stream()
//						.noneMatch(nonAva -> nonAva.getDay() == element.getDay()
//								&& nonAva.getPeriod() == element.getPeriod());
//				if (!isAvailable) {
//					counter++;
//				}
//			}
//		}
//		return counter;
//	}
//
//
//
//	public int isTeacherClassConflictSatisfied() {
//	       int counter = 0;
//	        List<Teacher> teachers = schoolTimetable.getTeachers();
//	        for (Teacher teacher : teachers) {
//	            List<ModuleElement> elements = (List<ModuleElement>) teacher.getModuleElements();
//	            if (elements != null) {
//	                for (ModuleElement element : elements) {
//	                    boolean isSatisfied = elements.stream()
//	                            .noneMatch(elementCompare ->
//	                                    element.getDay() == elementCompare.getDay() &&
//	                                            element.getModule().getLesson().getSemester().getNumber().ordinal()%2==elementCompare.getModule().getLesson().getSemester().getNumber().ordinal()%2 &&
//	                                            		elementCompare.getPeriod() == element.getPeriod() &&
//	                                            				elementCompare.getId() != element.getId());
//	                    if (!isSatisfied) {
//	                    	counter++;
//	                    }
//	                }
//	            }
//	        }
//	        return counter;
//	    }
//
//
//
//	public int isLessonSessionsConflictSatisfied() {
//		 int counter = 0;
//	        List<Classes> classes = schoolTimetable.getLessons();
//	        for (Classes lesson : classes) {
//	            List<Module> modules = (List<Module>) lesson.getModules();
//	            if (modules != null) {
//	                for (Module module : modules) {
//	                    List<ModuleElement> elements = (List<ModuleElement>) module.getModuleElements();
//	                    if (elements != null) {
//	                        for (ModuleElement element : elements) {
//	                            for (Module module1 : modules) {
//	                                List<ModuleElement> elements1 = (List<ModuleElement>) module1.getModuleElements();
//	                                boolean isSatisfied = elements1.stream()
//	                                        .noneMatch(elementCompar ->
//	                                                element.getDay() == elementCompar.getDay() &&
//	                                                        elementCompar.getPeriod() == element.getPeriod() &&
//	                                                        elementCompar.getId() != element.getId() &&
//	                                                        element.getModule().getLesson().getSemester().getNumber().ordinal()%2==elementCompar.getModule().getLesson().getSemester().getNumber().ordinal()%2
//	                                                         );
//	                                if (!isSatisfied) {
//	                                	counter++;
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	        return counter;
//	    }
//
//
//
//	public int isClassroomOccupancySatisfied() {
//		int counter = 0;
//        List<Module> modules = schoolTimetable.getModules();
//        for (Module module : modules) {
//            List<ModuleElement> elements = (List<ModuleElement>) module.getModuleElements();
//            if (elements != null) {
//                for (ModuleElement element : elements) {
//                    Classroom room = element.getClassroom();
//                    if (room != null && room.getModuleElements() != null) {
//                        boolean isOccupied = room.getModuleElements().stream()
//                                .anyMatch(sc ->
//                                        sc.getDay() == element.getDay() &&
//                                                sc.getPeriod() == element.getPeriod()
//                                                && element.getId() != sc.getId() &&
//                                                sc.getModule().isMutual()==false &&
//                                                element.getModule().getLesson().getSemester().getNumber().ordinal()%2==sc.getModule().getLesson().getSemester().getNumber().ordinal()%2 &&
//                                                sc.getModule().getLesson().getMajor().getDepartment()== element.getModule().getLesson().getMajor().getDepartment()
//
//                                );
//                        if (isOccupied) {
//                        	counter++;
//                        }
//                    }
//                }
//            }
//        }
//        return counter;
//    }
//
//	public int areElementsAdjacent() {
//
//		int counter = 0;
//        List<Module> modules = schoolTimetable.getModules();
//        for (Module module : modules) {
//            if (!module.isSeperated()) {
//                List<ModuleElement> elements = (List<ModuleElement>) module.getModuleElements();
//                if (elements != null) {
//                	ModuleElement currentElement = elements.get(0);
//                	ModuleElement nextElement = elements.get(1);
//                    if ((currentElement.getDay() != nextElement.getDay() &&
//                    currentElement.getPeriod().ordinal() != nextElement.getPeriod().ordinal() - 1)|| (currentElement.getDay() != nextElement.getDay() &&
//                            currentElement.getPeriod().ordinal() != nextElement.getPeriod().ordinal() + 1)) {
//
//                        counter++;
//
//                    }
//                }
//            }
//        }
//        return counter;
//    }
//
//
//	public int areElementsInTheSamePeriod() {
//		int counter = 0;
//        List<Classes> lessons = schoolTimetable.getLessons();
//        for (Classes lesson : lessons) {
//            List<Module> modules = (List<Module>) lesson.getModules();
//            if (modules != null) {
//                for (Module module : modules) {
//                    if (module.isMutual()) {
//                        List<ModuleElement> elements = (List<ModuleElement>) module.getModuleElements();
//                        if (elements != null) {
//                            for (ModuleElement element : elements) {
//                                for (Classes otherClasse : lessons) {
//                                    if (otherClasse != lesson && otherClasse.getMajor().equals(lesson.getMajor())) {
//                                        List<Module> otherModules = (List<Module>) otherClasse.getModules();
//                                        if (otherModules != null) {
//                                            for (Module otherModule : otherModules) {
//                                                if (otherModule.getLabel().equals(module.getLabel())) {
//                                                    List<ModuleElement> otherElements = (List<ModuleElement>) otherModule.getModuleElements();
//                                                    if (otherElements != null) {
//                                                        boolean isSatisfied = otherElements.stream()
//                                                                .noneMatch(otherElement ->
//                                                                        otherElement.getPeriod() == element.getPeriod()
//                                                                );
//                                                        if (!isSatisfied) {
//                                                            counter++;
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return counter;
//    }

}
