package com.alialperen.timeTableGenerator.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.ModuleElement;
import com.alialperen.timeTableGenerator.repository.ModuleElementRepository;
import com.alialperen.timeTableGenerator.service.ModuleElementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleElementServiceImpl implements ModuleElementService {

	private final ModuleElementRepository moduleElementRepository;

	@Override
	public List<ModuleElement> getModuleElement() {
		// TODO Auto-generated method stub
		return moduleElementRepository.findAll();
	}

	@Override
	public ModuleElement addModuleElement(ModuleElement moduleElement) {
		// TODO Auto-generated method stub
		return moduleElementRepository.save(moduleElement);
	}

	@Override
	public String deleteModuleElement(Long id) {
		try {
			getModuleElementById(id);
			moduleElementRepository.deleteById(id);
			return "DELETED SUCCESSFULLY";
		}catch(Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public ModuleElement getModuleElementById(Long id) {
		// TODO Auto-generated method stub
		return moduleElementRepository.findById(id).orElseThrow(() -> new RuntimeException("This module not found with id " + id));
	}

	@Override
	public ModuleElement updateModuleElement(Long id, ModuleElement moduleElement) {
		// TODO Auto-generated method stub
		moduleElement.setId(id);
		return moduleElementRepository.save(moduleElement);
	}

	@Override
	public List<ModuleElement> getSchedulesByClass(Long classId) {
		// TODO Auto-generated method stub
		return moduleElementRepository.getSchedulesByClass(classId);
	}
	
	
}
