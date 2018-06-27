package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.SchoolYearEntity;

public interface SchoolYearRepository extends CrudRepository<SchoolYearEntity, Integer>{
	public List<SchoolYearEntity>  findByActive(Boolean t);
}
