package com.alialperen.timeTableGenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alialperen.timeTableGenerator.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {

}
