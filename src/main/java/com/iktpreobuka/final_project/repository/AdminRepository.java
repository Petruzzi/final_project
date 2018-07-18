package com.iktpreobuka.final_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.AdminEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer>{

}
