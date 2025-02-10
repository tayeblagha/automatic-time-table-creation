package com.alialperen.timeTableGenerator.service;

import com.alialperen.timeTableGenerator.dto.AuthResponse;
import com.alialperen.timeTableGenerator.dto.SignInRequest;
import com.alialperen.timeTableGenerator.dto.SignUpRequest;
import com.alialperen.timeTableGenerator.entity.User;

public interface AuthService {
	
	

    public AuthResponse login(String login, String password);
    public void logout(Long id);
	

}
