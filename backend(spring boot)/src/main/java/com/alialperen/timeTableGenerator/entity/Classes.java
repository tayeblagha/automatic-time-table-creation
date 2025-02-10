package com.alialperen.timeTableGenerator.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Classes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "label")
	private String label;

	@Min(1)
	@Max(5)
	@Column(name = "grade")
	private int grade;

	@Column(name = "number_of_students")
	private int numberOfStudents;

	@ManyToMany
	@JoinTable(
			name = "class_major",
			joinColumns = @JoinColumn(name = "class_id"),
			inverseJoinColumns = @JoinColumn(name = "major_id")
	)
	private Collection<Major> majors = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department ;


	@OneToMany(mappedBy = "classes", cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<RoomOrder> roomOrders = new ArrayList<>();


	@OneToMany(mappedBy = "classes", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Student> students = new ArrayList<>();




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Min(1)
	@Max(5)
	public int getGrade() {
		return grade;
	}

	public void setGrade(@Min(1) @Max(5) int grade) {
		this.grade = grade;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public Collection<Major> getMajors() {
		return majors;
	}

	public void setMajors(Collection<Major> majors) {
		this.majors = majors;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


}




