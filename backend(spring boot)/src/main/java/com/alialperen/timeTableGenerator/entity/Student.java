package com.alialperen.timeTableGenerator.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {
    @Column
    private String profilePicture;

    @ManyToOne
    @JoinColumn(name = "class_id") // Adds a foreign key column for Classes
    private Classes classes;

    public Student() {
    }
    public Student(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "Student{" +

                ", classes=" + classes +
                '}';
    }
}
