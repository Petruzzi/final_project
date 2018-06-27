package com.iktpreobuka.final_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.SubjectEntity;
import com.iktpreobuka.final_project.entities.SubjectGradeEntity;

public interface SubjectGradeRepository extends CrudRepository<SubjectGradeEntity, Integer> {
	public List<SubjectGradeEntity> findAllByProfessors(ProfessorEntity pe);
	//public List<SubjectEntity> findAllByGrades(GradeEntity ge);
}
