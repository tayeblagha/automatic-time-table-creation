package com.alialperen.timeTableGenerator.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.NotAvailable;
import com.alialperen.timeTableGenerator.entity.Teacher;
import com.alialperen.timeTableGenerator.repository.NotAvailableRepository;
import com.alialperen.timeTableGenerator.service.NotAvailableService;
import com.alialperen.timeTableGenerator.service.TeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotAvailableServiceImp implements NotAvailableService {
	
	private final NotAvailableRepository notAvailableRepository;
	
	private final TeacherService teacherService;
	
	@Override
	public List<NotAvailable> getNotAvailables() {
		// TODO Auto-generated method stub
		return notAvailableRepository.findAll();
	}

	@Override
	public NotAvailable addNotAvailable(NotAvailable notAvailable) {
		// TODO Auto-generated method stub
		return notAvailableRepository.save(notAvailable);
	}

	@Override
	public String deleteNotAvailable(Long id) {
		try {
			getNotAvailableById(id);
			notAvailableRepository.deleteById(id);
            return "Deleted Successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
	}

	@Override
	public NotAvailable getNotAvailableById(Long id) {
		// TODO Auto-generated method stub
		 return notAvailableRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
	}

	@Override
	public NotAvailable updateNotAvailable(Long id, NotAvailable notAvailable) {
		//notAvailable.setId(id);
        return notAvailableRepository.save(notAvailable);
	}

	@Override
	public List<NotAvailable> getNotAvailableByTeacherId(Long id) throws Exception {
		Teacher teacher = teacherService.findTeacherById(id);
		List<NotAvailable> notAvailables = new ArrayList<>();
		
//		for(NotAvailable notAvailable : teacher.getNotAvailables()) {
//			notAvailables.add(notAvailable);
//		}
		return notAvailables;
	}

}
