package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.Student;

public class TeacherStudentService {
    static final Database db = new Database();

    public List<Student> getAllStudentsFromClass(int class_id) {
        return db.getAllStudentsFromClass(class_id);
    }

    public Student getStudent(int student_id) {
        return db.getStudent(student_id);
    }
}
