package com.alialperen.timeTableGenerator.dto;

import jakarta.persistence.Embeddable;

@Embeddable

public class TeacherDto {
    private long id;
    private String name ;

    public TeacherDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
