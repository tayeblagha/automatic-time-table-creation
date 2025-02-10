package com.alialperen.timeTableGenerator.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.alialperen.timeTableGenerator.service.PdfExportService;
//import com.lowagie.text.DocumentException;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequestMapping("/api/pdf")
//@RequiredArgsConstructor
public class PDFExportController {
//
//	private final PdfExportService pdfExportService;
//
//	@GetMapping("/classes")
//	public void generateAll(HttpServletResponse response) throws DocumentException, IOException {
//		this.pdfExportService.ClassPDF(response);
//	}
//
//    @GetMapping("/departments/{id}")
//    public void generateDepartement(HttpServletResponse response,@PathVariable Long id) throws IOException {
//        this.pdfExportService.DepartmentsPDF(response,id);
//    }
//
//
//    @GetMapping("/classes/{id}")
//    public void generatePDFbyClass(HttpServletResponse response,@PathVariable Long id) throws Exception {
//        this.pdfExportService.OneClassesPdf(response, id);
//    }
//
//    @GetMapping("/teachers/{id}")
//    public void generatePDFbyProf(HttpServletResponse response,@PathVariable Long id) throws Exception {
//        this.pdfExportService.TeacherPDF(response, id);
//    }
//
////    @GetMapping("/teachers")
////    public void generatePDFbyProf(HttpServletResponse response) throws IOException {
////        this.pdfExportService.AllTeachersPdf(response);
////    }
//
////    @GetMapping("/teacher/{id}")
////    public void generatePDFbyTeacher(HttpServletResponse response,@PathVariable Long id) throws Exception {
////        this.pdfExportService.TeachersAllPDF(response,id);
////
////    }
}
