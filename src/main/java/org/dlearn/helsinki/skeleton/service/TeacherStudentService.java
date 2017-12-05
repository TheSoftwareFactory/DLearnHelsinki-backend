package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;

public class TeacherStudentService {
    static final Database db = new Database();

    public List<Student> getAllStudentsFromClass(int class_id) {
        return db.getAllStudentsFromClass(class_id).orElse(null);
    }

    public Student getStudent(int student_id) {
        return db.getStudent(student_id);
    }

    public List<Student> getAllStudents() {//(int teacher_id)
        return db.getAllStudents();//teacher_id);
    }

    public boolean doesStudentIdExistInDatabase(int student_id) {
        return db.doesStudentIdExistInDatabase(student_id);
    }
}
