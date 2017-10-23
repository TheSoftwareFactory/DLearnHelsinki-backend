package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;

public class MoveToGroupService {
    private static final Database DB = new Database();

    public boolean moveStudentToGroup(int class_id, int student_id, int group_id) {
        return DB.addStudentToGroup(DB.getStudent(student_id), class_id, group_id);
    }

	public void removeStudentFromClass(Student student, int class_id) {
		DB.removeStudenFromClass(student,class_id);
	}
}
