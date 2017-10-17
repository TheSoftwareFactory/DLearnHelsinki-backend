package org.dlearn.helsinki.skeleton.service;

import java.util.List;
import java.util.Optional;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;

public class GroupService {
    private final Database db = new Database();
    
    public Optional<Group> getTheGroupStudentIsIn(int class_id, int studentID) {
        return db.getGroupForStudent(class_id, studentID);
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
}
