package com.alialperen.timeTableGenerator.entity;

import java.io.Serializable;
import java.util.Objects;

public class TeacherClassMajorId implements Serializable {

    private long classes; // Use the ID type of Classes
    private long major;   // Use the ID type of Major

    // Default constructor
    public TeacherClassMajorId() {}

    public TeacherClassMajorId(long classes, long major) {
        this.classes = classes;
        this.major = major;
    }

    // Getters, setters, equals, and hashCode
    public long getClasses() {
        return classes;
    }

    public void setClasses(long classes) {
        this.classes = classes;
    }

    public long getMajor() {
        return major;
    }

    public void setMajor(long major) {
        this.major = major;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherClassMajorId that = (TeacherClassMajorId) o;
        return classes == that.classes && major == that.major;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classes, major);
    }
}
