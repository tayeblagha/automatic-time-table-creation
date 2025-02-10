package com.alialperen.timeTableGenerator.entity;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="Module")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private long id;
    
    @Column(name="label")
    private String label;
    
    
    @Column(name="isSeperated")
    private boolean isSeperated;
    
    @Column(name="isMutual")
    private boolean isMutual;
    
    @Column(name="classRate")
    private int classRate;
    
    
    @OneToMany(mappedBy="module",fetch=FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<ModuleElement> moduleElements = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name="lesson")
    private Classes lesson;
    
    

}
