package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;

public class TeacherClassStudentService {
	
	Database db = new Database();

	public List<StudentThemeAverage> getStudentThemeAverage(int survey_id, int student_id) {
		// TODO Auto-generated method stub
		return db.getStudentThemeAverage(survey_id,student_id);
	}
	
	

}
