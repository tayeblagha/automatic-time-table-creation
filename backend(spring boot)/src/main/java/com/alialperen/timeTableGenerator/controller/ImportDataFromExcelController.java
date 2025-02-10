package com.alialperen.timeTableGenerator.controller;

import java.io.File;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alialperen.timeTableGenerator.config.LoadFromExcelToDatabase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class ImportDataFromExcelController {
//
//	private final LoadFromExcelToDatabase loadFormExcelToDb;
//
//    @PostMapping("/import")
//    public Boolean importData(@RequestParam("file") MultipartFile file) throws Exception {
//        try {
//            File convertedFile = convertMultipartFileToFile(file);
//            return loadFormExcelToDb.PutDataToDatabase(convertedFile.getPath());
//        } catch (IOException e) {
//            return false;
//        }
//    }
//
//    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
//        String tempDir = System.getProperty("java.io.tmpdir");
//        File file = new File(tempDir + "/" + Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        try (var inputStream = multipartFile.getInputStream()) {
//            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//        }
//        return file;
//    }
}
