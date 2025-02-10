package com.alialperen.timeTableGenerator.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import com.alialperen.timeTableGenerator.entity.Classroom;

public interface ClassroomService {

	Classroom createClassroom(Classroom classroom);
    Classroom findClassromById(long id) throws Exception ;
    public String deleteClassroom(Long id);
    Classroom updateClassroom(Classroom classroom,long classroomId);
    public List<Classroom> findAllClassrooms();
    
    
    public Page<Classroom> searchClassrooms(int keyword, Pageable pageable);
    
    Page<Classroom> getClassrooms(Pageable pageable);

    public Page<Classroom> findByLabelContaining(String label, Pageable pageable);
    public Page<Classroom> findByLabelContaining(String label, String typeRoom, Pageable pageable);

}
