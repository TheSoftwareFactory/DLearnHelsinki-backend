package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Student;

public class CreateNewStudentService {

    private final Database db = new Database();

    public Student createNewStudent(Student student) {
        // TODO: Check that age is positive, password isn't too short.
        return db.createStudent(student);
    }

}
