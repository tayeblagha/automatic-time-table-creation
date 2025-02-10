package com.alialperen.timeTableGenerator.service.imp;


import org.springframework.stereotype.Service;

import com.alialperen.timeTableGenerator.entity.User;
import com.alialperen.timeTableGenerator.entity.enums.Role;
import com.alialperen.timeTableGenerator.repository.UserRepository;
import com.alialperen.timeTableGenerator.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
//	
//	private final UserRepository userRepository;
//	
//	@PostConstruct
//	public void createAdminAccount() {
//		User adminAccount = userRepository.findByRole("ADMIN");
//		
//		if(adminAccount == null) {
//			User admin = new User();
//			admin.setEmail("admin@admin.com");
//			admin.setPassword("admin");
//			admin.setFirstName("Admin");
//			admin.setRole("ADMIN");
//			userRepository.save(admin);
//		}
//
//		
//	}


}
