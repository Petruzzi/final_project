package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer>{
	//public List<SubjectEntity> findAllByProfessors(ProfessorEntity pe);
	//public List<SubjectEntity> findAllByGrades(GradeEntity ge);
}
