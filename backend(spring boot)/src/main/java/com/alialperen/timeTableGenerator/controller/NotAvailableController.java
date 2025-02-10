package com.alialperen.timeTableGenerator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alialperen.timeTableGenerator.entity.NotAvailable;
import com.alialperen.timeTableGenerator.service.NotAvailableService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notAvailables")
@RequiredArgsConstructor
public class NotAvailableController {
	
	private final NotAvailableService notAvailableService;
	
	@GetMapping
    public List<NotAvailable> getAllNotAvailables() {
        return notAvailableService.getNotAvailables();
    }

    @GetMapping("/{id}")
    public NotAvailable getlNotAvailableById(@PathVariable Long id) {
        return notAvailableService.getNotAvailableById(id);
    }

    @PostMapping
    public NotAvailable createlNotAvailable(@RequestBody NotAvailable notAvailable) {
        return notAvailableService.addNotAvailable(notAvailable);
    }

    @PutMapping("/{id}")
    public NotAvailable updatelNotAvailable(@PathVariable Long id, @RequestBody NotAvailable updatedNotAvailable) {
        return notAvailableService.updateNotAvailable(id, updatedNotAvailable);
    }

    @DeleteMapping("/{id}")
    public String deletelNotAvailable(@PathVariable Long id) {
        return notAvailableService.deleteNotAvailable(id);
    }
    
    @GetMapping("/teacher/{id}")
    public List<NotAvailable> getlNotAvailableByTeacherId(@PathVariable Long id) throws Exception {
        return notAvailableService.getNotAvailableByTeacherId(id);
    }
    
    

}
