package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.entities.AbsenceRecordEntity;

public interface AbsenceRecordDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getAbsenceRecordById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewAbsenceRecord(AbsenceRecordEntity areBody);
	
	public ResponseEntity<?> putAbsenceRecordById(AbsenceRecordEntity areBody, String idString);
}
