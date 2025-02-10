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

import com.alialperen.timeTableGenerator.service.ModuleService;

import lombok.RequiredArgsConstructor;

import com.alialperen.timeTableGenerator.entity.Module;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {
	
	private final ModuleService moduleService;

	
	@GetMapping
    public List<Module> getAllModules() {
        return moduleService.getModules();
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return moduleService.addModule(module);
    }

    @PutMapping("/{id}")
    public Module updateModule(@PathVariable Long id, @RequestBody Module updatedModule) {
        return moduleService.updateModule(id, updatedModule);
    }

    @DeleteMapping("/{id}")
    public String deleteModule(@PathVariable Long id) {
        return moduleService.deleteModule(id);
    }
}
