package com.iktpreobuka.final_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{

}
