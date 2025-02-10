package com.alialperen.timeTableGenerator.service.imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alialperen.timeTableGenerator.entity.Classroom;
import com.alialperen.timeTableGenerator.entity.Classes;
import com.alialperen.timeTableGenerator.repository.ClassroomRepository;
import com.alialperen.timeTableGenerator.service.ClassroomService;
import com.alialperen.timeTableGenerator.repository.ClassesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImp implements ClassroomService {
	
	private final ClassroomRepository classroomRepository;

	@Override
	public Classroom createClassroom(Classroom classroom) {
		// TODO Auto-generated method stub
		return classroomRepository.save(classroom);
	}

	@Override
	public Classroom findClassromById(long id) throws Exception {
		   return classroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom is not exist!"));
    }


	@Override
	public String deleteClassroom(Long id) {
		try {
			findClassromById(id);
			classroomRepository.deleteById(id);
            return "Classroom deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
	}

	@Override
	public Classroom updateClassroom(Classroom classroom, long classroomId) {
		classroom.setId(classroomId);
		
		
		return classroomRepository.save(classroom);
	}

	@Override
	public List<Classroom> findAllClassrooms() {
		// TODO Auto-generated method stub
		return classroomRepository.findAll();
	}

	@Override
	public Page<Classroom> searchClassrooms(int keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return classroomRepository.searchWithPagination(keyword, pageable);
	}

	@Override
	public Page<Classroom> getClassrooms(Pageable pageable) {
		// TODO Auto-generated method stub
		return classroomRepository.findAll(pageable);
	}

	@Override
	public Page<Classroom> findByLabelContaining(String label, Pageable pageable) {
		return classroomRepository.findByLabelContaining(label,pageable);
	}
	@Override
	public Page<Classroom> findByLabelContaining(String label,String typeRoom, Pageable pageable) {
		return classroomRepository.findByLabelContaining(label,typeRoom,pageable);
	}


}
