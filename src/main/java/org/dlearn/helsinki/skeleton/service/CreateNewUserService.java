package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;

public class CreateNewUserService {

    private final Database db = new Database();

    public Student createNewStudent(NewStudent newStudent) {
        // TODO: Check that age is positive, password isn't too short.
        Student student = db.createStudent(newStudent);
        db.addStudentToGroup(student, newStudent.group_id, newStudent.class_id);
        return student;
    }

    public Teacher createNewTeacher(NewTeacher newTeacher) {
        return db.createTeacher(newTeacher);
    }

}
