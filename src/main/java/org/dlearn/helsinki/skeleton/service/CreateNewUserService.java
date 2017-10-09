package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.exceptions.StudentExistsException;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;

public class CreateNewUserService {

    private final Database db = new Database();

    public Student createNewStudent(NewStudent newStudent) throws RuntimeException{
    	Student student = null;
        // TODO: Check that age is positive, password isn't too short.
    	if(!db.doesStudentUsernameExistInDatabase(newStudent.student)) {
            student = db.createStudent(newStudent);
            db.addStudentToGroup(student, newStudent.group_id, newStudent.class_id);
    	} else {
    		throw new StudentExistsException();
    	};
        return student;
    }

    public Teacher createNewTeacher(NewTeacher newTeacher) {
        return db.createTeacher(newTeacher);
    }

}
