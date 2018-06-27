package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ParentEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>{
	public List<StudentEntity> findAllByParents(ParentEntity pe);
}
