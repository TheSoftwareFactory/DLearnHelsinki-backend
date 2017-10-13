package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;

public class MoveToGroupService {
    private static final Database DB = new Database();

    public void moveStudentToGroup(int class_id, int student_id, int group_id) {
        DB.addStudentToGroup(DB.getStudent(student_id), class_id, group_id);
    }
}
