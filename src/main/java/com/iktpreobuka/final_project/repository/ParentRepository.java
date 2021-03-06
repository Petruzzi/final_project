package com.iktpreobuka.final_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer>{

	public ParentEntity findByEmail(String email);
}
