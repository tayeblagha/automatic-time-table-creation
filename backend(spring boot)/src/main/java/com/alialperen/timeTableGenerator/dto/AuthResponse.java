package com.alialperen.timeTableGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthResponse {
    private String token;
    private boolean isAdmin;
    private String name;
    private Long id;
    private boolean isTeacher;
    private boolean isAuthenticated;

    public AuthResponse() {
        this.token = "";
        this.isAdmin = false;
        this.id = 0L;
        this.name = "";
        this.isTeacher = false;
        this.isAuthenticated = false;
    }
}
