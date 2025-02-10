package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.entity.SchoolGeneralInfo;

import java.util.Optional;

public interface SchoolGeneralInfoService {
    public SchoolGeneralInfo createSchoolIfNotFound();
    public Optional<SchoolGeneralInfo> getSchoolGeneralInfo();
    public SchoolGeneralInfo updateSchoolGeneralInfo(SchoolGeneralInfo updatedInfo);
}
