package com.alialperen.timeTableGenerator.service;

import java.awt.Color;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.config.DataFromDb;
import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.entity.Department;
import com.alialperen.timeTableGenerator.entity.Major;
import com.alialperen.timeTableGenerator.entity.ModuleElement;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.entity.enums.Period;
import com.alialperen.timeTableGenerator.repository.ModuleElementRepository;
import com.alialperen.timeTableGenerator.repository.ModuleRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfExportService {
//
//	private final DataFromDb dataFromDb;
//
//	private final ModuleElementRepository moduleElementRepository;
//
//	private final ModuleRepository moduleRepository;
//
//	private final ClassesService classService;
//
//	private final TeacherService teacherService;
//
//	private final DepartmentService departmentService;
//
//	Period[] timeSlots;
//	List<DayOfWeek> days;
//
//
//	public void TeacherPDF(HttpServletResponse response,Long id) throws Exception {
//		dataFromDb.loadDataFromDatabase();
//
//		Document myPdfDocument = new Document(PageSize.A4,
//                40f,   // left
//                40f,   // right
//                70f,  // top
//                70f); // down
//
//
//		final PdfWriter pdfWriter = PdfWriter.getInstance(myPdfDocument, response.getOutputStream());
//		myPdfDocument.open();
//		Teacher teacher = teacherService.findTeacherById(id);
//
//		AddPageTeacher(myPdfDocument,teacher);
//
//		myPdfDocument.close();
//		pdfWriter.close();
//
//	}
//
//
//	public void AllTeachersPdf(HttpServletResponse response) throws DocumentException, IOException {
//		dataFromDb.loadDataFromDatabase();
//
//		Document myPDFDoc = new Document(PageSize.A4,
//                40f,
//                40f,
//                70f,
//                70f);
//
//		final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
//        myPDFDoc.open();
//
//        for(Teacher teacher : DataFromDb.teachers) {
//        	AddPageTeacher(myPDFDoc,teacher);
//        }
//
//
//        myPDFDoc.close();
//        pdfWriter.close();
//	}
//
//	public void DepartmentsPDF(HttpServletResponse response, long id) throws DocumentException, IOException {
//		dataFromDb.loadDataFromDatabase();
//
//        Document myPDFDoc = new Document(PageSize.A4,
//                40f,
//                40f,
//                70f,
//                70f);
//
//        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
//
//        Department department = departmentService.getDepartmentById(id);
//        myPDFDoc.open();
//
//        for(Major major : department.getMajors()) {
//        	for(Classes classes : major.getLessons()) {
//        		AddPageClass(myPDFDoc,classes);
//       }
//     }
//        myPDFDoc.close();
//        pdfWriter.close();
//
//   }
//
//	public void OneClassesPdf(HttpServletResponse response, long id) throws Exception {
//		dataFromDb.loadDataFromDatabase();
//
//		Classes classes = classService.findLessonById(id);
//
//        Document myPDFDoc = new Document(PageSize.A4,
//                40f,
//                40f,
//                70f,
//                70f);
//
//        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
//        myPDFDoc.open();
//
//        AddPageClass(myPDFDoc,classes);
//        myPDFDoc.close();
//        pdfWriter.close();
//	}
//
//
//    public void ClassPDF(HttpServletResponse response) throws IOException {
//        dataFromDb.loadDataFromDatabase();
//
//
//        Document myPDFDoc = new Document(PageSize.A4,
//                40f,
//                40f,
//                70f,
//                70f);
//
//        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
//        myPDFDoc.open();
//        for(Classes classe:DataFromDb.lessons){
//
//            AddPageClass(myPDFDoc,classe);
//
//        }
//
//        myPDFDoc.close();
//        pdfWriter.close();
//
//
//    }
//
//
//
//
//	   public void AddPageClass(Document myPDFDoc, Classes classes) throws IOException {
//
//	        timeSlots = Period.values();
//	        days = new ArrayList<>();
//	        days.add(DayOfWeek.MONDAY);
//	        days.add(DayOfWeek.TUESDAY);
//	        days.add(DayOfWeek.WEDNESDAY);
//	        days.add(DayOfWeek.THURSDAY);
//	        days.add(DayOfWeek.FRIDAY);
//
//
//
//	        String title = classes.getLabel();
//
//
//	        Table myTable = new Table(6);
//
//
//	        FontFactory.register("Fonts/QuattrocentoSans-Italic.ttf");
//
//
//	        FontFactory.register("Fonts/Calibri Regular.ttf");
//
//	        Font Calibri1 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//	        Font Calibri2 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//	        Font Calibri3 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10);
//
//	        Font Quatt = FontFactory.getFont("Quattrocento Sans Italic", BaseFont.WINANSI, 9,Font.ITALIC, Color.BLUE);
//
//
//
//	        float[] columnWidths = { 25f, 30f, 30f, 30f, 30f, 30f };
//	        myTable.setWidths(columnWidths);
//	        myTable.setPadding(2f);
//	        myTable.setWidth(100f);
//
//
//	        ArrayList<String> headerTable = new ArrayList<>();
//	        headerTable.add("");
//	        headerTable.add("08:30 - 10:00");
//	        headerTable.add("10:00 - 12:00");
//	        headerTable.add("13:00 - 15:00");
//	        headerTable.add("15:00 - 17:00");
//	        headerTable.add("17:00 - 19:00");
//
//	        headerTable.forEach(e -> {
//	            Paragraph paragraph = new Paragraph(e,Calibri1);
//	            paragraph.setAlignment(Element.ALIGN_CENTER);
//	            Cell current = new Cell(paragraph);
//	            current.setHeader(true);
//	            current.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            current.setBackgroundColor(Color.LIGHT_GRAY);
//	            myTable.addCell(current);
//	        });
//
//
//
//
//
//
//	        LinkedHashMap<Integer, List<List<Paragraph>>> listRows = new LinkedHashMap<>();
//	        int rowNumber = 1;
//
//
//	        for(DayOfWeek day:days){
//	            List<Paragraph> CellList = new ArrayList<>();
//	            List<List<Paragraph>> dayList = new ArrayList<>();
//	            Paragraph Day;
//	            if(rowNumber==1){
//	                Day = new Paragraph("\n\nMonday\n\n\n",Calibri2);
//	            } else if (rowNumber==2) {
//	                Day = new Paragraph("\n\nTuesday\n\n\n",Calibri2);
//	            } else if (rowNumber==3) {
//	                Day = new Paragraph("\n\nWednesday\n\n\n",Calibri2);
//	            } else if (rowNumber==4) {
//	                Day = new Paragraph("\n\nThursday\n\n\n",Calibri2);
//	            } else{
//	                Day = new Paragraph("\n\nFriday\n\n\n",Calibri2);
//	            }
//	            Day.setAlignment(Element.ALIGN_CENTER);
//	            ArrayList<Paragraph> dayCell = new ArrayList<>();
//	            dayCell.add(Day);
//	            dayList.add(dayCell);
//
//	            for(Period p :timeSlots){
//	                CellList = new ArrayList<>();
//
//
//	                List<ModuleElement> elms = moduleElementRepository.findByDayOfWeekAndPeriodAndLesson(day,p, classes.getId());
//	                System.out.println("Elements "+elms.size());
//	                if(elms.size()>0){
//	                    Paragraph label = new Paragraph(elms.get(0).getLabel()+" ("+elms.get(0).getModule().getClassRate()+")",Calibri1);
//	                    label.setAlignment(Element.ALIGN_CENTER);
//	                    Paragraph teacher = new Paragraph(elms.get(0).getTeacher().getFirstName()+" "+elms.get(0).getTeacher().getLastName(),Calibri3);
//	                    teacher.setAlignment(Element.ALIGN_CENTER);
//	                    Paragraph room = new Paragraph("Room : "+elms.get(0).getClassroom().getTypeRoom()+" "+elms.get(0).getClassroom().getNumRoom(),Quatt);
//	                    room.setAlignment(Element.ALIGN_CENTER);
//	                    CellList.add(label);
//	                    CellList.add(teacher);
//	                    CellList.add(room);
//
//	                }
//	                else{
//	                    CellList.add(new Paragraph(""));
//	                }
//	                dayList.add(CellList);
//
//
//
//	            }
//	            System.out.println(dayList);
//	            listRows.put(rowNumber, dayList);
//	            rowNumber++;
//
//
//	        }
//
////	        Paragraph cumartesi = new Paragraph("\n\nCumartesi\n\n\n",Calibri2);
////	        List<Paragraph> Cellist = new ArrayList<>();
////	        Cellist.add(cumartesi);
////
////	        List<List<Paragraph>> DayList = new ArrayList<>();
////	        DayList.add(Cellist);
////
////	        Cellist = new ArrayList<>();
////
////
////	        DayList.add(Cellist);
////	        Cellist = new ArrayList<>();
////	        DayList.add(Cellist);
////	        Cellist = new ArrayList<>();
////	        Cellist.add(new Paragraph(""));
////	        DayList.add(Cellist);
////	        listRows.put(6, DayList);
//
//	        listRows.forEach((index,userDetailRow) -> {
//	            userDetailRow.forEach(paragraphs -> {
//	                Cell cell = new Cell();
//	                paragraphs.forEach(paragraph ->{
//	                    cell.add(paragraph);
//	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
//
//	                });
//	                myTable.addCell(cell);
//
//
//	            });
//	        });
//
//
//
//
//	        myPDFDoc.addTitle("Exam Schedule");
//	        myPDFDoc.addCreator("Cumhuriyet University");
//	        myPDFDoc.addAuthor("Ali Alperen TANIR");
//
//
//
//	        FontFactory.register("Fonts/COMIC.ttf");
//	        FontFactory.register("Fonts/times new roman.ttf");
//
//	        Font font1 = FontFactory.getFont("Comic Sans MS", 14, Font.UNDERLINE);
//	        Font font2 = FontFactory.getFont("Times New Roman", 14);
//	        Font font3 = FontFactory.getFont("Times New Roman", 12);
//	        Font font4 = FontFactory.getFont("Calibri", 20,Font.BOLD);
//	        Font font5 = FontFactory.getFont("Calibri", 18,Font.BOLD);
//	        Font font6 = FontFactory.getFont("Calibri", 16, Font.BOLD,Color.RED);
//	        int year = new Date().getYear();
//	        String text1 = "Department : "+classes.getMajor().getDepartment().getLabel();
//	        String text2 = "Exam Schedule";
//	        String text3 = Integer.toString(year+1900)+" / "+Integer.toString(year+1+1900);
//	        String yil = classes.getLabel().split(" ")[1];
//	        String text4 = classes.getMajor().getLabel();
//
//
//
//	        String text5 = "Semester "+classes.getLabel().split(" ")[1];
//
//
//	        Paragraph paragraph1 = new Paragraph(text1,font1);
//	        paragraph1.setAlignment(Element.ALIGN_CENTER);
//	        Paragraph paragraph2 = new Paragraph(text2,font2);
//	        paragraph2.setAlignment(Element.ALIGN_CENTER);
//	        Paragraph paragraph3 = new Paragraph(text3,font3);
//	        paragraph3.setAlignment(Element.ALIGN_CENTER);
//	        Paragraph paragraph4 = new Paragraph(text4,font4);
//	        paragraph4.setAlignment(Element.ALIGN_CENTER);
//	        Paragraph paragraph5 = new Paragraph(text5,font5);
//	        paragraph5.setAlignment(Element.ALIGN_CENTER);
//
//
//	        Image headerImage = Image.getInstance("src/main/resources/header.png");
//
//	        float headerWidth = PageSize.A4.getWidth();
//	        float headerHeight = 70f;
//	        Rectangle headerRect = new Rectangle(headerWidth, headerHeight);
//	        headerImage.setAbsolutePosition(0, PageSize.A4.getHeight() - headerHeight - 10f);
//	        headerImage.scaleToFit(headerWidth, headerHeight);
//	        myPDFDoc.add(headerImage);
//
//
//	        Image footerImage = Image.getInstance("src/main/resources/footer.jpg");
//	        float footerWidth = PageSize.A4.getWidth();
//	        float footerHeight = 80f;
//	        Rectangle footerRect = new Rectangle(footerWidth, footerHeight);
//	        footerImage.setAbsolutePosition(0, 90f);
//	        footerImage.scaleToFit(footerWidth, footerHeight);footerImage.scaleToFit(footerWidth, footerHeight);
//	        myPDFDoc.add(footerImage);
//
//
//
//
//
//	        myPDFDoc.add(paragraph1);
//	        myPDFDoc.add(paragraph2);
//	        myPDFDoc.add(paragraph3);
//	        myPDFDoc.add(paragraph4);
//	        myPDFDoc.add(paragraph5);
//
//	        myPDFDoc.add(new Paragraph(Chunk.NEWLINE));
//
//
//
//	        myPDFDoc.add(myTable);
//	        myPDFDoc.newPage();
//	    }
//
//
//	public void AddPageTeacher(Document myPdfDoc, Teacher teacher) throws BadElementException, IOException {
//
//		timeSlots = Period.values();
//		days = new ArrayList<>();
//        days.add(DayOfWeek.MONDAY);
//        days.add(DayOfWeek.TUESDAY);
//        days.add(DayOfWeek.WEDNESDAY);
//        days.add(DayOfWeek.THURSDAY);
//        days.add(DayOfWeek.FRIDAY);
//
//        String title = teacher.getFirstName()+" " + teacher.getLastName();
//
//        Table myTable = new Table(6);
//
//
//        FontFactory.register("Fonts/QuattrocentoSans-Italic.ttf");
//
//
//        FontFactory.register("Fonts/Calibri Regular.ttf");
//
//        Font Calibri1 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//        Font Calibri2 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//        Font Calibri3 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 8);
//
//        Font Quatt = FontFactory.getFont("Quattrocento Sans Italic", BaseFont.WINANSI, 9,Font.ITALIC, Color.BLUE);
//
//
//        float[] columnWidths = { 25f, 30f, 30f, 30f, 30f, 30f };
//        myTable.setWidths(columnWidths);
//        myTable.setPadding(2f);
//        myTable.setWidth(100f);
//
//
//        ArrayList<String> headerTable = new ArrayList<>();
//        headerTable.add("");
//        headerTable.add("08:30 - 10:00");
//        headerTable.add("10:00 - 12:00");
//        headerTable.add("13:00 - 15:00");
//        headerTable.add("15:00 - 17:00");
//        headerTable.add("17:00 - 19:00");
//
//        headerTable.forEach(e -> {
//            Paragraph paragraph = new Paragraph(e,Calibri1);
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            Cell current = new Cell(paragraph);
//            current.setHeader(true);
//            current.setHorizontalAlignment(Element.ALIGN_CENTER);
//            current.setBackgroundColor(Color.LIGHT_GRAY);
//            myTable.addCell(current);
//        });
//
//        LinkedHashMap<Integer, List<List<Paragraph>>> listRows = new LinkedHashMap<>();
//        int rowNum = 1;
//
//        for(DayOfWeek day:days){
//            List<Paragraph> CellList = new ArrayList<>();
//            List<List<Paragraph>> dayList = new ArrayList<>();
//            Paragraph Day;
//            if(rowNum==1){
//                Day = new Paragraph("\n\nMonday\n\n\n",Calibri2);
//            } else if (rowNum==2) {
//                Day = new Paragraph("\n\nTuesday\n\n\n",Calibri2);
//            } else if (rowNum==3) {
//                Day = new Paragraph("\n\nWednesday\n\n\n",Calibri2);
//            } else if (rowNum==4) {
//                Day = new Paragraph("\n\nThursday\n\n\n",Calibri2);
//            } else{
//                Day = new Paragraph("\n\nFriday\n\n\n",Calibri2);
//            }
//            Day.setAlignment(Element.ALIGN_CENTER);
//            ArrayList<Paragraph> dayCell = new ArrayList<>();
//            dayCell.add(Day);
//            dayList.add(dayCell);
//
//            for(Period p :timeSlots){
//                CellList = new ArrayList<>();
//
//                List<ModuleElement> element = moduleElementRepository.findByDayOfWeekAndPeriodAndTeacher(day, p, teacher.getId());
//                System.out.println("Elements " + element.size());
//
//                if(element.size() > 0) {
//                	Paragraph label = new Paragraph(element.get(0).getLabel(),Calibri1);
//                	label.setAlignment(Element.ALIGN_CENTER);
//                	//Paragraph classes = new Paragraph(element.get(0).getModule().getLesson().getLabel(),Calibri3);
//                	//classes.setAlignment(Element.ALIGN_CENTER);
//                	Paragraph classroom = new Paragraph("Room :" + element.get(0).getClassroom().getTypeRoom()+" "+element.get(0).getClassroom().getNumRoom(),Quatt);
//                	classroom.setAlignment(Element.ALIGN_CENTER);
//                	CellList.add(label);
//                	//CellList.add(classes);
//                	CellList.add(classroom);
//
//                }else {
//                	CellList.add(new Paragraph(""));
//                }
//                dayList.add(CellList);
//            }
//
//            System.out.println(dayList);
//            listRows.put(rowNum, dayList);
//            rowNum++;
//        }
//
//        listRows.forEach((index,userDetailRow) ->{
//        	userDetailRow.forEach(paragraphs ->{
//        		Cell cell = new Cell();
//        		paragraphs.forEach(paragraph ->{
//        			cell.add(paragraph);
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
//
//        		});
//        		myTable.addCell(cell);
//        	});
//        });
//
//        myPdfDoc.addTitle("Teacher's Schedule");
//        myPdfDoc.addCreator("Cumhuriyet University");
//        myPdfDoc.addAuthor("Ali Alperen TANIR");
//
//        FontFactory.register("Fonts/COMIC.ttf");
//        FontFactory.register("Fonts/times new roman.ttf");
//
//        Font font1 = FontFactory.getFont("Comic Sans MS", 14, Font.UNDERLINE);
//        Font font2 = FontFactory.getFont("Times New Roman", 14);
//        Font font3 = FontFactory.getFont("Times New Roman", 12);
//        Font font4 = FontFactory.getFont("Calibri", 20,Font.BOLD);
//        Font font5 = FontFactory.getFont("Calibri", 18,Font.BOLD);
//        Font font6 = FontFactory.getFont("Calibri", 16, Font.BOLD,Color.RED);
//        int year = new Date().getYear();
//
//        String text1 = "Teacher: "+ teacher.getFirstName() + " "+ teacher.getLastName();
//        String text2 = "Schedule";
//        String text3 = Integer.toString(year + 1900) + " / " + Integer.toString(year+1+1900);
//
//        Paragraph paragraph1 = new Paragraph(text1,font1);
//        paragraph1.setAlignment(Element.ALIGN_CENTER);
//        Paragraph paragraph2 = new Paragraph(text2,font2);
//        paragraph2.setAlignment(Element.ALIGN_CENTER);
//        Paragraph paragraph3 = new Paragraph(text3,font3);
//        paragraph3.setAlignment(Element.ALIGN_CENTER);
//
//
//
//        Image headerImage = Image.getInstance("src/main/resources/header.png");
//        float headerWidth = PageSize.A4.getWidth();
//        float headerHeight = 70f;
//
//        Rectangle headerRect = new Rectangle(headerWidth, headerHeight);
//        headerImage.setAbsolutePosition(0, PageSize.A4.getHeight() - headerHeight - 10f);
//        headerImage.scaleToFit(headerWidth, headerHeight);
//        myPdfDoc.add(headerImage);
//
//
//        Image footerImage = Image.getInstance("src/main/resources/footer.jpg");
//        float footerWidth = PageSize.A4.getWidth();
//        float footerHeight = 80f;
//        Rectangle footerRect = new Rectangle(footerWidth, footerHeight);
//        footerImage.setAbsolutePosition(0, 90f);
//        footerImage.scaleToFit(footerWidth, footerHeight);footerImage.scaleToFit(footerWidth, footerHeight);
//        myPdfDoc.add(footerImage);
//
//
//        myPdfDoc.add(paragraph1);
//        myPdfDoc.add(paragraph2);
//        myPdfDoc.add(paragraph3);
//
//        myPdfDoc.add(new Paragraph(Chunk.NEWLINE));
//
//
//        myPdfDoc.add(myTable);
//        myPdfDoc.newPage();
//	}
//
//	public void AddPageTeacherAndClass(Document myPdfDoc, Teacher teacher,Classes classes) throws BadElementException, IOException {
//
//		timeSlots = Period.values();
//		days = new ArrayList<>();
//        days.add(DayOfWeek.MONDAY);
//        days.add(DayOfWeek.TUESDAY);
//        days.add(DayOfWeek.WEDNESDAY);
//        days.add(DayOfWeek.THURSDAY);
//        days.add(DayOfWeek.FRIDAY);
//
//        String title = teacher.getFirstName()+" " + teacher.getLastName();
//
//        Table myTable = new Table(6);
//
//
//        FontFactory.register("Fonts/QuattrocentoSans-Italic.ttf");
//
//
//        FontFactory.register("Fonts/Calibri Regular.ttf");
//
//        Font Calibri1 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//        Font Calibri2 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 10,Font.BOLD);
//
//        Font Calibri3 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 8);
//
//        Font Quatt = FontFactory.getFont("Quattrocento Sans Italic", BaseFont.WINANSI, 9,Font.ITALIC, Color.BLUE);
//
//
//        float[] columnWidths = { 25f, 30f, 30f, 30f, 30f, 30f };
//        myTable.setWidths(columnWidths);
//        myTable.setPadding(2f);
//        myTable.setWidth(100f);
//
//
//        ArrayList<String> headerTable = new ArrayList<>();
//        headerTable.add("");
//        headerTable.add("08:30 - 10:00");
//        headerTable.add("10:00 - 12:00");
//        headerTable.add("13:00 - 15:00");
//        headerTable.add("15:00 - 17:00");
//        headerTable.add("17:00 - 19:00");
//
//        headerTable.forEach(e -> {
//            Paragraph paragraph = new Paragraph(e,Calibri1);
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            Cell current = new Cell(paragraph);
//            current.setHeader(true);
//            current.setHorizontalAlignment(Element.ALIGN_CENTER);
//            current.setBackgroundColor(Color.LIGHT_GRAY);
//            myTable.addCell(current);
//        });
//
//        LinkedHashMap<Integer, List<List<Paragraph>>> listRows = new LinkedHashMap<>();
//        int rowNum = 1;
//
//        for(DayOfWeek day:days){
//            List<Paragraph> CellList = new ArrayList<>();
//            List<List<Paragraph>> dayList = new ArrayList<>();
//            Paragraph Day;
//            if(rowNum==1){
//                Day = new Paragraph("\n\nMonday\n\n\n",Calibri2);
//            } else if (rowNum==2) {
//                Day = new Paragraph("\n\nTuesday\n\n\n",Calibri2);
//            } else if (rowNum==3) {
//                Day = new Paragraph("\n\nWednesday\n\n\n",Calibri2);
//            } else if (rowNum==4) {
//                Day = new Paragraph("\n\nThursday\n\n\n",Calibri2);
//            } else{
//                Day = new Paragraph("\n\nFriday\n\n\n",Calibri2);
//            }
//            Day.setAlignment(Element.ALIGN_CENTER);
//            ArrayList<Paragraph> dayCell = new ArrayList<>();
//            dayCell.add(Day);
//            dayList.add(dayCell);
//
//            for(Period p :timeSlots){
//                CellList = new ArrayList<>();
//
//                List<ModuleElement> element = moduleElementRepository.findByDayOfWeekAndPeriodAndLessonAndTeacher(day, p,classes.getId(),teacher.getId());
//                System.out.println("Elements " + element.size());
//
//                if(element.size() > 0) {
//                	Paragraph label = new Paragraph(element.get(0).getLabel(),Calibri1);
//                	label.setAlignment(Element.ALIGN_CENTER);
//                	//Paragraph classes = new Paragraph(element.get(0).getModule().getLesson().getLabel(),Calibri3);
//                	//classes.setAlignment(Element.ALIGN_CENTER);
//                	Paragraph classroom = new Paragraph("Room :" + element.get(0).getClassroom().getTypeRoom()+" "+element.get(0).getClassroom().getNumRoom(),Quatt);
//                	classroom.setAlignment(Element.ALIGN_CENTER);
//                	CellList.add(label);
//                	//CellList.add(classes);
//                	CellList.add(classroom);
//
//                }else {
//                	CellList.add(new Paragraph(""));
//                }
//                dayList.add(CellList);
//            }
//
//            System.out.println(dayList);
//            listRows.put(rowNum, dayList);
//            rowNum++;
//        }
//
//        listRows.forEach((index,userDetailRow) ->{
//        	userDetailRow.forEach(paragraphs ->{
//        		Cell cell = new Cell();
//        		paragraphs.forEach(paragraph ->{
//        			cell.add(paragraph);
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
//
//        		});
//        		myTable.addCell(cell);
//        	});
//        });
//
//        myPdfDoc.addTitle("Teacher's Schedule");
//        myPdfDoc.addCreator("Cumhuriyet University");
//        myPdfDoc.addAuthor("Ali Alperen TANIR");
//
//        FontFactory.register("Fonts/COMIC.ttf");
//        FontFactory.register("Fonts/times new roman.ttf");
//
//        Font font1 = FontFactory.getFont("Comic Sans MS", 14, Font.UNDERLINE);
//        Font font2 = FontFactory.getFont("Times New Roman", 14);
//        Font font3 = FontFactory.getFont("Times New Roman", 12);
//        Font font4 = FontFactory.getFont("Calibri", 20,Font.BOLD);
//        Font font5 = FontFactory.getFont("Calibri", 18,Font.BOLD);
//        Font font6 = FontFactory.getFont("Calibri", 16, Font.BOLD,Color.RED);
//        int year = new Date().getYear();
//
//        String text1 = "Teacher: "+ teacher.getFirstName() + " "+ teacher.getLastName();
//        String text2 = "Schedule";
//        String text3 = Integer.toString(year + 1900) + " / " + Integer.toString(year+1+1900);
//
//        Paragraph paragraph1 = new Paragraph(text1,font1);
//        paragraph1.setAlignment(Element.ALIGN_CENTER);
//        Paragraph paragraph2 = new Paragraph(text2,font2);
//        paragraph2.setAlignment(Element.ALIGN_CENTER);
//        Paragraph paragraph3 = new Paragraph(text3,font3);
//        paragraph3.setAlignment(Element.ALIGN_CENTER);
//
//
//
//        Image headerImage = Image.getInstance("src/main/resources/header.png");
//        float headerWidth = PageSize.A4.getWidth();
//        float headerHeight = 70f;
//
//        Rectangle headerRect = new Rectangle(headerWidth, headerHeight);
//        headerImage.setAbsolutePosition(0, PageSize.A4.getHeight() - headerHeight - 10f);
//        headerImage.scaleToFit(headerWidth, headerHeight);
//        myPdfDoc.add(headerImage);
//
//
//        Image footerImage = Image.getInstance("src/main/resources/footer.jpg");
//        float footerWidth = PageSize.A4.getWidth();
//        float footerHeight = 80f;
//        Rectangle footerRect = new Rectangle(footerWidth, footerHeight);
//        footerImage.setAbsolutePosition(0, 90f);
//        footerImage.scaleToFit(footerWidth, footerHeight);footerImage.scaleToFit(footerWidth, footerHeight);
//        myPdfDoc.add(footerImage);
//
//
//        myPdfDoc.add(paragraph1);
//        myPdfDoc.add(paragraph2);
//        myPdfDoc.add(paragraph3);
//
//        myPdfDoc.add(new Paragraph(Chunk.NEWLINE));
//
//
//        myPdfDoc.add(myTable);
//        myPdfDoc.newPage();
//	}
//


}

