package com.alialperen.timeTableGenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alialperen.timeTableGenerator.entity.NotAvailable;

public interface NotAvailableRepository extends JpaRepository<NotAvailable, Long> {

}
