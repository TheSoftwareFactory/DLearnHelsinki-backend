package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.StudentGroup;

public class TeacherGroupService {

	Database db = new Database();

	public List<StudentGroup> getGroupsWithStudents(int class_id) {
		// TODO Auto-generated method stub
		return db.getGroupsWithStudents(class_id);
	}
	
	
}
