package com.alialperen.timeTableGenerator.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="Department")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	private long id;

    @Column()
    private String name;
    @Column(name="label")
    private String label;



    @OneToMany(mappedBy = "department")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Classes> classes = new ArrayList<>();


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Collection<Classes> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
