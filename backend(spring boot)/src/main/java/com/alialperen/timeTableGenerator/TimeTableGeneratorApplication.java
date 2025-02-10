package com.alialperen.timeTableGenerator;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Stream;

import com.alialperen.timeTableGenerator.service.AutomatedInfo;
import com.alialperen.timeTableGenerator.service.SchoolGeneralInfoService;
import com.alialperen.timeTableGenerator.service.SemesterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.Department;
import com.alialperen.timeTableGenerator.entity.Admin;
import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.Semester;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.entity.User;
import com.alialperen.timeTableGenerator.entity.Module;
import com.alialperen.timeTableGenerator.entity.ModuleElement;
import com.alialperen.timeTableGenerator.entity.NotAvailable;

import com.alialperen.timeTableGenerator.repository.*;

@SpringBootApplication
public class TimeTableGeneratorApplication {


    public static void main(String[] args) {
        SpringApplication.run(TimeTableGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner lineRunner(UserRepository userRepository, SemesterService semesterService, SchoolGeneralInfoService schoolGeneralInfoService, AutomatedInfo automatedInfo) {
        return args -> {
            // Check if an admin already exists in the database
            User existingUser = userRepository.findByLogin("admin");
            if (existingUser==null) {
                Admin admin = new Admin();
                admin.setFirstName("Tayeb");
                admin.setLastName("Lagha");
                admin.setEmail("admin@admin.com");
                admin.setPassword("admin");
                admin.setLogin("admin");
                userRepository.save(admin);
                semesterService.createDefaultSemesters();
                schoolGeneralInfoService.createSchoolIfNotFound();
                automatedInfo.AutomateData();
                System.out.println("Admin user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }

}



//	@Bean
//	CommandLineRunner lineRunner(UserRepository userRepository) {
//		return args -> {
//			Admin admin = new Admin();
//			admin.setFirstName("Admin");
//			admin.setLastName("ADMIN");
//			admin.setEmail("admin@test.com");
//			admin.setPassword("12345");
//			admin.setLogin("admin");
//			userRepository.save(admin);
//			
////			User adminAccount = userRepository.findByRole(Role.ADMIN);
////
////			if (adminAccount == null) {
////				User user = new User();
////
////				user.setSchoolNumber("admin@test.com");
////				user.setName("admin");
////				user.setRole(Role.ADMIN);
////				user.setPassword(new BCryptPasswordEncoder().encode("admin"));
////
////				userRepository.save(user);
//			}
//			
//			//CREATE DEPARTMENT
//			Department department1 = new Department();
//			department1.setLabel("Department-1");
//			department1.setChefDepartment("DChef-1");
//			departmentRepository.save(department1);
//			
//			Department department2 = new Department();
//			department2.setLabel("Department-2");
//			department2.setChefDepartment("DChef-2");
//			departmentRepository.save(department2);
//			
//			// CREATE MAJOR
//			Major major1 = new Major();
//			major1.setLabel("Major-1");
//			major1.setChefMajor("MChef-1");
//			major1.setDepartment(department1);
//			major1.setNumOfSem(2);
//			majorRepository.save(major1);
//			
//			Major major2 = new Major();
//			major2.setLabel("Major-2");
//			major2.setChefMajor("MChef-2");
//			major2.setDepartment(department2);
//			major2.setNumOfSem(2);
//			majorRepository.save(major2);
//			
//			
//			Semester semester1 = new Semester();
//			semester1.setStartDate(new Date());
//			semester1.setEndDate(new Date());
//			semester1.setNumber(SemesterNumber.S1);
//			semester1.setAcademicYear("2023-2024");
//			semesterRepository.save(semester1);
//			
//			Semester semester2 = new Semester();
//			semester2.setStartDate(new Date());
//			semester2.setEndDate(new Date());
//			semester2.setNumber(SemesterNumber.S2);
//			semester2.setAcademicYear("2023-2024");
//			semesterRepository.save(semester2);
//			
//			Classes lesson = new Classes();
//			lesson.setName("Class-1");
//			lesson.setLabel("Class 1");
//			lesson.setGrade(1);
//			lesson.setPeopleTakingCourse(110);
//			lesson.setMajor(major1);
//			lesson.setSemester(semester1);
//			lessonRepository.save(lesson);
//			
//			Classes lesson1 = new Classes();
//			lesson1.setName("Class-2");
//			lesson1.setLabel("Class 2");
//			lesson1.setGrade(2);
//			lesson1.setMajor(major1);
//			lesson1.setPeopleTakingCourse(110);
//			lesson1.setSemester(semester2);
//			lessonRepository.save(lesson1);
//
//			
//////**************************Class-1 için ders ekleme ************************************
//			List<Module> modules = new ArrayList<>();
//			Stream.of("Module-1","Module-2","Module-3","Module-4","Module-5","Module-6","Module-7"
//					).forEach(label -> {
//						Module module = new Module();
//						module.setLabel(label);
//						module.setMutual(false);
//						module.setHourlyVolume(50);
//						module.setSeperated(true);
//						module.setLesson(lesson);
//						moduleRepository.save(module);
//						modules.add(module);
//					});
//			
//			List<Teacher> teachers = new ArrayList<>();
//			Stream.of("Teacher-16","Teacher-11","Teacher-12","Teacher-13","Teacher-14","Teacher-15",
//					  "Teacher-1","Teacher-2","Teacher-3","Teacher-4","Teacher-5","Teacher-6","Teacher-7","Teacher-8","Teacher-9","Teacher-10"
//					 ).forEach(name ->{
//						 Teacher teacher = new Teacher();
//						 String[] names = name.split(" ");
//				          if(names.length == 2) {
//				              teacher.setFirstName(names[0]);
//				              teacher.setLastName(names[1]);
//				          } else {
//				              teacher.setFirstName(name);
//				              teacher.setLastName("");
//				          }
//						 teacher.setEmail(name + "@example.com");
//						 teacher.setPassword("12345");
//						 teacher.setLogin(name);
//						 userRepository.save(teacher);
//						 teachers.add(teacher);
//					 });
//			
//			int[] moduleIndices = {0,0,1,2,3,4,4,5,5,6,6,1,2,3};
//			int[] teacherIndices = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; 
//			int[] roomNum = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}; 
//			String[] roomsNames = {"Room-11","Room-12","Room-13","Room-14","FD-1","FD-2","Room-1","Room-2","Room-3","Room-4","Room-5",
//					"Room-6","Room-7","Room-8","Room-9","Room-10"};
//			Stream.of(11,7,4,9,5,13,1,12,53,10,43,21,17,22,33,44).forEach(num ->{
//				Classroom classroom = new Classroom();
//				classroom.setCapacity(110);
//				classroom.setTypeRoom(TypeRoom.big);
//				classroom.setNumRoom(num);
//				
//				//??????????????????????????
//				classroomRepository.save(classroom);
//			});
//			
//			String[]labels = {"Element-1","Element-2","Element-3",
//					"Element-4","Element-5","Element-6","Element-7","Element-11","Element-12","Element-13","Element-14",
//					 "Element-8","Element-9","Element-10"};
//			
//			for(int i = 0;i<moduleIndices.length;i++) {
//				ModuleElement moduleElement = new ModuleElement();
//				moduleElement.setLabel(labels[i]);
//				moduleElement.setClassRate(1);
//				moduleElement.setModule(modules.get(moduleIndices[i]));
//				moduleElement.setTeacher(teachers.get(teacherIndices[i]));
//				
//				moduleElementRepository.save(moduleElement);
//			}
//			
//			
//			
//			NotAvailable notAvailable = new NotAvailable();
//			notAvailable.setDay(DayOfWeek.MONDAY);
//			notAvailable.setPeriod(Period.P1);
//			notAvailable.setTeacher(teachers.get(0));
//			
//			notAvailableRepository.save(notAvailable);
//
//			
//			NotAvailable notAvailable1 = new NotAvailable();
//			notAvailable1.setDay(DayOfWeek.THURSDAY);
//			notAvailable1.setPeriod(Period.P3);
//			notAvailable1.setTeacher(teachers.get(1));
//			
//			notAvailableRepository.save(notAvailable1);
//			
//			
//			
//			


//**************************BIL-2 için ders ekleme *********************************************
//			List<Module> modules2 = new ArrayList<>();
//			Stream.of("Sayısal Analiz","Otomata","OOP","Olasılık"
//					).forEach(label -> {
//						Module module = new Module();
//						module.setLabel(label);
//						module.setMutual(false);
//						module.setSeperated(true);
//						module.setLesson(lesson1);
//						moduleRepository.save(module);
//						modules2.add(module);
//					});
//			
//			List<Teacher> teachers1 = new ArrayList<>();
//			Stream.of("Kali Gülkahraman","Bilal Kasap","Abdulkadir Şeker","Saliha Yeşilyurt","Halil Arslan"
//					 ).forEach(name ->{
//						 Teacher teacher = new Teacher();
//						 teacher.setName(name);
//						 teacher.setEmail(name + "@example.com");
//						 teacher.setPassword("12345");
//						 teacherRepository.save(teacher);
//						 teachers1.add(teacher);
//					 });
//			
//			
//			int[] moduleIndices1 = {0,1,2,3};
//			int[] teacherIndices1 = {0,1,2,3,4,5}; 
//			String[] roomsNames1 = {"Bilgisayar-1","Bilgisayar-2","Bilgisayar-3","Bilgisayar-4","Yazılım-1","Yazılım-2"};
//			Stream.of(110,75,84,94,54,132).forEach(num ->{
//				Classroom classroom = new Classroom();
//				classroom.setCapacity(num);
//				for(int i = 0;i<roomsNames1.length;i++) {
//					classroom.setName(roomsNames1[i]);
//				}
//				classroomRepository.save(classroom);
//			});
//			
//			
//String[]labels1 = {"Bilgisayar Ve Matematik","Otomatların Çalışma Manttığı","Nesne Tabanlı Programlama","Olasılık ve Bilgisayar"};
//			
//			for(int i = 0;i<moduleIndices.length;i++) {
//				ModuleElement moduleElement = new ModuleElement();
//				moduleElement.setLabel(labels1[i]);
//				moduleElement.setModule(modules2.get(moduleIndices1[i]));
//				moduleElement.setTeacher(teachers1.get(teacherIndices1[i]));
//				
//				moduleElementRepository.save(moduleElement);
//			}
//			
//			
//			
//			NotAvailable notAvailable2 = new NotAvailable();
//			notAvailable.setDay(DayOfWeek.FRIDAY);
//			notAvailable.setPeriod(Period.P2);
//			notAvailable.setTeacher(teachers.get(0));
//			
//			notAvailableRepository.save(notAvailable2);
//
//			
//			NotAvailable notAvailable3 = new NotAvailable();
//			notAvailable1.setDay(DayOfWeek.WEDNESDAY);
//			notAvailable1.setPeriod(Period.P3);
//			notAvailable1.setTeacher(teachers.get(1));
//			
//			notAvailableRepository.save(notAvailable3);
			
			

	
	
		

	

//}
