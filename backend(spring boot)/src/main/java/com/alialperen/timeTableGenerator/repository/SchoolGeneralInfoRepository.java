package com.alialperen.timeTableGenerator.repository;

import com.alialperen.timeTableGenerator.entity.SchoolGeneralInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchoolGeneralInfoRepository extends JpaRepository<SchoolGeneralInfo, Long> {
    @Query("SELECT s FROM SchoolGeneralInfo s ORDER BY s.id ASC")
    Optional<SchoolGeneralInfo> findFirst();
}
