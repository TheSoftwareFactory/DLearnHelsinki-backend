package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;


public class StudentService {	
    static final Database db = new Database();

    public Student getStudent(int student_id) {
        return db.getStudent(student_id);
    }
}
