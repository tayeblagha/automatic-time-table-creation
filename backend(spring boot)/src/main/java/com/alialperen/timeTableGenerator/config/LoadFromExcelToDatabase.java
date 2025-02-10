package com.alialperen.timeTableGenerator.config;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.Department;
import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Semester;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.entity.enums.Period;
import com.alialperen.timeTableGenerator.entity.enums.SemesterNumber;
import com.alialperen.timeTableGenerator.entity.enums.TypeRoom;
import com.alialperen.timeTableGenerator.entity.Module;
import com.alialperen.timeTableGenerator.entity.ModuleElement;
import com.alialperen.timeTableGenerator.entity.NotAvailable;
import com.alialperen.timeTableGenerator.service.*;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoadFromExcelToDatabase {
//
//	private final ClassroomService classroomService;
//
//	private final TeacherService teacherService;
//
//	private final DepartmentService departmentService;
//
//	private final MajorService majorService;
//
//	private final ClassesService classService;
//
//	private final ModuleService moduleService;
//
//	private final ModuleElementService moduleElementService;
//
//	private final SemesterService semesterService;
//
//	private final NotAvailableService notAvaliableService;
//
//	public boolean PutDataToDatabase(String path) throws Exception {
//
//		boolean isImported = true;
//
//		try {
//
//			Workbook workbook = WorkbookFactory.create(new File(path));
//
//			int numberOfSheets = workbook.getNumberOfSheets();
//
//			List<Semester> semesters = new ArrayList<>();
//
//			for (int i = 0; i < numberOfSheets; i++) {
//				Sheet sheet = workbook.getSheetAt(i);
//				if (sheet.getSheetName().contains("CLASSROOMS")) {
//					Classroom room = null;
//					for (Row row : sheet) {
//						if (row.getRowNum() >= 4) {
//							for (Cell cell : row) {
//								int roomNumber = -1;
//								if (cell.getColumnIndex() == 2) {
//									room = new Classroom();
//									if (!cell.getStringCellValue().equals("")) {
//										roomNumber = Integer.parseInt(cell.getStringCellValue().split("_")[1]);
//										room.setNumRoom(roomNumber);
//									}
//								}
//
//								if (cell.getColumnIndex() == 3) {
//									if (!cell.getStringCellValue().equals("")) {
//										assert room != null;
//										room.setTypeRoom(TypeRoom.valueOf(cell.getStringCellValue()));
//										room.setCapacity((int) cell.getRow().getCell(4).getNumericCellValue());
//									}
//									classroomService.createClassroom(room);
//								}
//							}
//						}
//					}
//				}
//
//				if (sheet.getSheetName().contains("TEACHERS")) {
//					Teacher t = null;
//					for (Row row : sheet) {
//						if (row.getRowNum() >= 4) {
//							for (Cell cell : row) {
//								if (cell.getColumnIndex() == 2) {
//									t = new Teacher();
//									if (!cell.getStringCellValue().equals("")) {
//										t.setFirstName(cell.getStringCellValue().split("_")[0]);
//										t.setLastName(cell.getStringCellValue().split("_")[1]);
//									}
//								}
////								if (cell.getColumnIndex() == 3) {
////									if (!cell.getStringCellValue().equals("")) {
//////										t.setSpeciality(cell.getStringCellValue());
////									}
////								}
//
//								if (cell.getColumnIndex() == 3) {
//									if (!cell.getStringCellValue().equals("")) {
//										t.setEmail(cell.getStringCellValue());
//									}
//								}
//
//								if (cell.getColumnIndex() == 4) {
//									if (!cell.getStringCellValue().equals("")) {
//										t.setLogin(cell.getStringCellValue());
//									}
//								}
//
//								if (cell.getColumnIndex() == 5) {
//									if (!cell.getStringCellValue().equals("")) {
//										t.setPassword(cell.getStringCellValue());
//									}
//								}
//
//								if (teacherService.findTeacherByName(t.getFirstName()).size() == 0) {
//									teacherService.createTeacher(t);
//								}
//
//							}
//						}
//					}
//				}
//
//				if (sheet.getSheetName().contains("AVAILABILITY")) {
//					NotAvailable n = null;
//					Teacher teacher = null;
//					for (Row row : sheet) {
//						if (row.getRowNum() >= 4) {
//							for (Cell cell : row) {
//								if (cell.getColumnIndex() == 2) {
//									n = new NotAvailable();
//									teacher = new Teacher();
//									if (!cell.getStringCellValue().equals("")) {
//										teacher.setFirstName(cell.getStringCellValue().split("_")[0]);
//										teacher.setLastName(cell.getStringCellValue().split("_")[1]);
//										teacher.setEmail(cell.getRow().getCell(3).getStringCellValue());
//										if (teacherService.findTeacherByName(teacher.getFirstName()).size() != 0) {
//											n.setTeacher(
//													teacherService.findTeacherByName(teacher.getFirstName()).get(0));
//										} else {
//											isImported = false;
//											System.out.println("Teacher is not exist");
//										}
//
//									}
//								}
//
//								/*if (cell.getColumnIndex() == 3) {
//									if (!cell.getStringCellValue().equals("")) {
//										teacher.setEmail(cell.getStringCellValue());
//										//n.setTeacher(teacher);
//									}
//								}*/
//
//								if (cell.getColumnIndex() == 4) {
//									if (!cell.getStringCellValue().equals("")) {
//										assert n != null;
//										n.setDay(DayOfWeek.valueOf(cell.getStringCellValue()));
//										n.setPeriod(Period.valueOf(cell.getRow().getCell(5).getStringCellValue()));
//									}
//
//									notAvaliableService.addNotAvailable(n);
//								}
//
//							}
//						}
//					}
//				}
//
//
//				if (!sheet.getSheetName().contains("CLASSROOMS") && !sheet.getSheetName().contains("TEACHERS") && !sheet.getSheetName().contains("AVAILABILITY")) {
//					Department department = new Department();
//					Major major = new Major();
//					major.setLessons(new ArrayList<>());
//					major.setLabel(sheet.getRow(2).getCell(3).getStringCellValue());
//					major.setChefMajor(sheet.getRow(2).getCell(4).getStringCellValue());
//					department.setLabel(sheet.getRow(2).getCell(5).getStringCellValue());
//					department.setChefDepartment(sheet.getRow(2).getCell(6).getStringCellValue());
//
//					DataFormatter dataFormatter = new DataFormatter();
//					Semester semester = null;
//					Classes classes = null;
//					Module module = null;
//					ModuleElement element;
//
//					int j = 5;
//					for (Row row : sheet) {
//						if (row.getRowNum() >= 6) {
//							j++;
//							for (Cell cell : row) {
//								if (cell.getColumnIndex() == 1) {
//									if (!cell.getStringCellValue().equals("")) {
//										semester = new Semester();
//										semester.setNumber(SemesterNumber.valueOf(cell.getStringCellValue()));
//										semester.setAcademicYear(sheet.getRow(0).getCell(2).getStringCellValue());
//										classes = new Classes();
//										classes.setLabel(
//												major.getLabel() + " " + semester.getNumber().toString().charAt(1));
//										if (semesterService.findSemesterByNum(semester.getNumber()).size() == 0) {
//											semesterService.addSemster(semester);
//											classes.setSemester(semester);
//										} else {
//											classes.setSemester(
//													semesterService.findSemesterByNum(semester.getNumber()).get(0));
//										}
//
//										major.getLessons().add(classes);
//										if (departmentService.findDepartmentByName(department.getLabel()).size() == 0) {
//											major.setDepartment(department);
//											departmentService.createDepartment(department);
//										} else {
//											major.setDepartment(departmentService
//													.findDepartmentByName(department.getLabel()).get(0));
//										}
//										majorService.createMajor(major);
//									}
//								}
//
//								if (cell.getColumnIndex() == 2) {
//									if (!cell.getStringCellValue().equals("")) {
//										module = new Module();
//										module.setModuleElements(new ArrayList<>());
//										module.setClassRate((int) sheet.getRow(j).getCell(7).getNumericCellValue());
//										module.setLabel(cell.getStringCellValue());
//										assert classes != null;
//										classes.getModules().add(module);
//									}
//									assert classes != null;
//									classes.setMajor(major);
//									classService.createLesson(classes);
//								}
//								if (cell.getColumnIndex() == 4) {
//									if (!cell.getStringCellValue().equals("")) {
//										element = new ModuleElement();
//										element.setLabel(cell.getStringCellValue());
//										Teacher t = null;
//										if (!cell.getRow().getCell(5).getStringCellValue().equals("")) {
//											t = new Teacher();
//											t.setFirstName(cell.getRow().getCell(5).getStringCellValue().split("_")[0]);
//											t.setLastName(cell.getRow().getCell(5).getStringCellValue().split("_")[1]);
//											if (teacherService.findTeacherByName(t.getFirstName()).size() != 0) {
//												element.setTeacher(
//														teacherService.findTeacherByName(t.getFirstName()).get(0));
//											} else {
//												isImported = false;
//												System.out.println("Teacher is not exist");
//											}
//										}
//
//										element.setPeopleTakingCourse((int) cell.getRow().getCell(6).getNumericCellValue());
//										moduleService.addModule(module);
//										element.setModule(module);
//
//										moduleElementService.addModuleElement(element);
//										assert module != null;
//										module.getModuleElements().add(element);
//										module.setLesson(classes);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//
//		} catch (EncryptedDocumentException | IOException e) {
//			isImported = false;
//			e.printStackTrace();
//		}
//
//		return isImported;
//	}
}
