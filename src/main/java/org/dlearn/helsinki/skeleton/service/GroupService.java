package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Group;

public class GroupService {
	
	Database db = new Database();
	
	public List<Group> getAllGroupsTheStudentIsIn(int studentID) {
		return db.getAllGroupsForStudent(studentID);
	}
}
