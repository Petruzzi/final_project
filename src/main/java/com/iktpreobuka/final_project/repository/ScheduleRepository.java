package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;

public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Integer>{
	public List<ScheduleEntity>  findByTeacher(ProfessorEntity pe);
}
