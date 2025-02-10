package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.entity.AcademicYear;
import com.alialperen.timeTableGenerator.entity.SchoolGeneralInfo;
import com.alialperen.timeTableGenerator.repository.SchoolGeneralInfoRepository;
import com.alialperen.timeTableGenerator.service.SchoolGeneralInfoService;
import com.alialperen.timeTableGenerator.service.SemesterService;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;
@Service
public class SchoolGeneralInfoServiceImp implements SchoolGeneralInfoService {
    private final SchoolGeneralInfoRepository schoolGeneralInfoRepository;
    private final SemesterService semesterService;
    public SchoolGeneralInfoServiceImp(SchoolGeneralInfoRepository schoolGeneralInfoRepository, SemesterService semesterService) {
        this.schoolGeneralInfoRepository = schoolGeneralInfoRepository;
        this.semesterService = semesterService;
    }

    @Override
    public SchoolGeneralInfo createSchoolIfNotFound() {
        // Attempt to retrieve the first SchoolGeneralInfo
        Optional<SchoolGeneralInfo> optionalSchool = schoolGeneralInfoRepository.findFirst();

        // If not found, create a new instance
        SchoolGeneralInfo school = optionalSchool.orElseGet(() -> {
            SchoolGeneralInfo newSchool = new SchoolGeneralInfo();
            newSchool.setName("Ecole D'ingenieur de Tunis");
            newSchool.setAcademicYear(new AcademicYear(Year.now().getValue()-1));
            newSchool.setNumberSemesters(3);
            newSchool.setImageUrl("https://raw.githubusercontent.com/smoothcoode/Image/refs/heads/main/school.png");
            return newSchool;
        });

        // Save and return the school
        return schoolGeneralInfoRepository.save(school);
    }


    // Method to get the school general information
    public Optional<SchoolGeneralInfo> getSchoolGeneralInfo() {
        return schoolGeneralInfoRepository.findFirst();
    }

    // Method to update the school general information
    public SchoolGeneralInfo updateSchoolGeneralInfo(SchoolGeneralInfo updatedInfo) {
        Optional<SchoolGeneralInfo> existingInfo = schoolGeneralInfoRepository.findFirst();

        if (existingInfo.isPresent()) {
            SchoolGeneralInfo school = existingInfo.get();
            school.setName(updatedInfo.getName());
            school.setImageUrl(updatedInfo.getImageUrl());
        if (school.getAcademicYear().getEndYear()!= updatedInfo.getAcademicYear().getEndYear()){
            school.setAcademicYear(updatedInfo.getAcademicYear());
            semesterService.updateCurrentYear(updatedInfo.getAcademicYear().getEndYear());
        }

            school.setNumberSemesters(updatedInfo.getNumberSemesters());
            return schoolGeneralInfoRepository.save(school);
        } else {
            throw new IllegalStateException("School general information does not exist. Please create it first.");
        }
    }

}
