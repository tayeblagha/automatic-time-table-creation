package com.alialperen.timeTableGenerator.controller;

import com.alialperen.timeTableGenerator.entity.SchoolGeneralInfo;
import com.alialperen.timeTableGenerator.service.SchoolGeneralInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class SchoolGeneralInfoController {
    private final SchoolGeneralInfoService schoolGeneralInfoService;
    @GetMapping("/")
    public Optional<SchoolGeneralInfo> getSchoolGeneralInfo() {
        return schoolGeneralInfoService.getSchoolGeneralInfo();
    }
    @PutMapping("/")
    public SchoolGeneralInfo updateSchoolGeneralInfo(@RequestBody SchoolGeneralInfo updatedInfo) {
        return  schoolGeneralInfoService.updateSchoolGeneralInfo(updatedInfo);
    }
}
