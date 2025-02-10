package com.alialperen.timeTableGenerator.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.Module;
import com.alialperen.timeTableGenerator.repository.ModuleRepository;
import com.alialperen.timeTableGenerator.service.ModuleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleServiceImp implements ModuleService {
	
	private final ModuleRepository moduleRepository;
	
	@Override
	public List<Module> getModules() {
		// TODO Auto-generated method stub
		return moduleRepository.findAll();
	}

	@Override
	public Module addModule(Module module) {
		// TODO Auto-generated method stub
		return moduleRepository.save(module);
	}

	@Override
	public String deleteModule(Long id) {
		try {
            getModuleById(id);
            moduleRepository.deleteById(id);
            return "Module deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
	}

	@Override
	public Module getModuleById(Long id) {
		// TODO Auto-generated method stub
		return moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
	}

	@Override
	public Module updateModule(Long id, Module module) {
		// TODO Auto-generated method stub
		module.setId(id);
		return moduleRepository.save(module);
	}

}
