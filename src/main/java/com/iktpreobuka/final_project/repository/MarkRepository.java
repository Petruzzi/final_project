package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.MarkEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer>{
	
	public List<MarkEntity> findByStudent(StudentEntity se);
	
	public List<MarkEntity> findByStudentIn(List<StudentEntity> se);
	
	public List<MarkEntity> findByScheduleIn(List<ScheduleEntity> se);
}
