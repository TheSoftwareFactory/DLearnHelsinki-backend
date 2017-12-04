package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;

public class StudentService {
    static final Database db = new Database();

    //TODO Student now does not contain password, so this service is redundant right now. It does the same as TeacherStudentService.
    public Student getStudent(int student_id) {
        return db.getStudent(student_id);
    }
}
