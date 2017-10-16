package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.StudentGroup;

public class TeacherGroupService {

    private static final Database DB = new Database();

    public List<StudentGroup> getGroupsWithStudents(int class_id) {
        return DB.getGroupsWithStudents(class_id);
    }

}
