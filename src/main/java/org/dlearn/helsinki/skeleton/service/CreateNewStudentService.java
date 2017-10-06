package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.Student;

public class CreateNewStudentService {

    private final Database db = new Database();

    public Student createNewStudent(NewStudent newStudent) {
        // TODO: Check that age is positive, password isn't too short.
        Student student = db.createStudent(newStudent);
        db.addStudentToGroup(student, newStudent.group_id);
        return student;
    }

}
