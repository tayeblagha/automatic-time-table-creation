package com.alialperen.timeTableGenerator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    Page<Teacher> findUsersByRole(String role, Pageable pageable);
    
 


    @Query("SELECT e FROM User e WHERE (e.firstName LIKE %?1%) AND e.role = 'TEACHER'")
    Page<Teacher> searchWithPagination(String keyword, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<Teacher> findAllByRole(String role);
    
    @Query("SELECT u FROM User u WHERE u.role = 'TEACHER' AND (u.firstName LIKE %?1%)")
    List<Teacher> findTeacherByName(String name);

    @Query("SELECT u FROM User u WHERE  u.password = ?1")
    User findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.login = ?1 AND u.password = ?2")
    User findByLoginAndPassword(String login, String password);


	

}
