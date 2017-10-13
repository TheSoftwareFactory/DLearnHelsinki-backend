package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentGroup;

public class TeacherGroupService {

    Database db = new Database();

    public List<StudentGroup> getGroupsWithStudents(int class_id) {
        // TODO Auto-generated method stub
        return db.getGroupsWithStudents(class_id);
    }
    
    public List<Group> getAllGroupsTheStudentIsIn(int studentID) {
        return db.getAllGroupsForStudent(studentID);
    }

    public List<Group> getAllGroupsFromClass(int class_id) {
        return db.getAllGroupsFromClass(class_id);
    }

    public Group getGroupFromClass(int class_id, int group_id) {
        return db.getGroupFromClass(class_id, group_id);
    }

    public List<Student> getAllStudentsFromClassAndGroup(int class_id,
            int group_id) {
        return db.getAllStudentsFromClassAndGroup(class_id, group_id);
    }
    
    public void deleteGroupFromClass(int class_id, int group_id) {
    	//db.deleteGroupFromClass(class_id, group_id);
    	// TODO We need to change the database structure so that deletion wont ruing history and integrity.
    }
}
