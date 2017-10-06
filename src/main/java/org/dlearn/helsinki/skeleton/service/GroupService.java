package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;

public class GroupService {
	
	Database db = new Database();
	
	public List<Group> getAllGroupsTheStudentIsIn(int studentID) {
		return db.getAllGroupsForStudent(studentID);
	}
	
	public List<Group> getAllGroupsFromClass(int class_id) {
		return db.getAllGroupsFromClass(class_id);
	}
	
	public Group getGroupFromClass(int class_id, int group_id) {
		return db.getGroupFromClass(class_id, group_id);
	}
	
	public List<Student> getAllStudentsFromClassAndGroup(int class_id, int group_id) {
		return db.getAllStudentsFromClassAndGroup(class_id, group_id);
	}
}
