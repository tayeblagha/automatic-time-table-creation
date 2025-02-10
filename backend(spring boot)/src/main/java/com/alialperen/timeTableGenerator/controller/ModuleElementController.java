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

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.service.ModuleElementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moduleElements")
public class ModuleElementController {
	
	private final ModuleElementService moduleElementService;
	
	@GetMapping
	public List<ModuleElement> getAllElements(){
		return moduleElementService.getModuleElement();
	}
	
	@GetMapping("/{id}")
	public ModuleElement getElementById(@PathVariable Long id) {
		return moduleElementService.getModuleElementById(id);
	}
	
	@PostMapping
	public ModuleElement createElement (@RequestBody ModuleElement element) {
		return moduleElementService.addModuleElement(element);
	}
	
	@PutMapping("/{id}")
	public ModuleElement updateModuleElement(@PathVariable Long id,@RequestBody ModuleElement updatedElement) {
		return moduleElementService.updateModuleElement(id, updatedElement);
	}
	
	@DeleteMapping("/{id}")
	public String deleteElement(@PathVariable Long id) {
		return moduleElementService.deleteModuleElement(id);
	}
	
	
	
	
	

}
