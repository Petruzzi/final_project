package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer>{
	public ClassEntity  findByHeadTeacher(ProfessorEntity pe);
	
	public List<ClassEntity> findBySchedulesIn(List<ScheduleEntity> se);
	
}
