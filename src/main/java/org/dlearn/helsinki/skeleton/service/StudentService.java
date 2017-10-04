package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;


public class StudentService {	
	Database db = new Database();

	public Student getStudent(int student_id) {
		// TODO Auto-generated method stub
		return db.getStudent(student_id);
	}
	
	public Student getStudentNoPassword(int student_id) {
		// TODO Auto-generated method stub
		return db.getStudent(student_id).setPassword(null);
	}
	
}
