package com.iktpreobuka.final_project.entities;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminEntity extends UserEntity{

	public AdminEntity() {
		super();
	}


	
}
