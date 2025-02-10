package com.alialperen.timeTableGenerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alialperen.timeTableGenerator.dto.AuthRequest;
import com.alialperen.timeTableGenerator.dto.AuthResponse;
import com.alialperen.timeTableGenerator.dto.SignInRequest;
import com.alialperen.timeTableGenerator.dto.SignUpRequest;
import com.alialperen.timeTableGenerator.entity.User;
import com.alialperen.timeTableGenerator.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        log.info("AuthController.login()...");
        return authService.login(authRequest.getUsername(), authRequest.getPassword());
    }
    
    
    @GetMapping("/logout/{id}")
    public void logout(@PathVariable Long id) {
        log.info("AuthController.logout()...");
        authService.logout(id);
    }
    
    
    
    
//	@PostMapping("/signup")
//	public ResponseEntity<User> signUp(@RequestBody SignUpRequest req) throws Exception{
//		return ResponseEntity.ok(authService.signUp(req));
//		
//	}
//	@PostMapping("/signin")
//	public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest req){
//		return ResponseEntity.ok(authService.signIn(req));
//	}

}
