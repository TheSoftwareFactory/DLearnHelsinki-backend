package org.dlearn.helsinki.skeleton.service;

import java.util.Optional;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.exceptions.StudentExistsException;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;

public class CreateNewUserService {

    private final Database db = new Database();

    public Optional<Student> createNewStudent(NewStudent newStudent) throws RuntimeException {
        // TODO: Check that age is positive, password isn't too short.
    	if(db.doesStudentUsernameExistInDatabase(newStudent.student)) {
            throw new StudentExistsException();
        }
        if (db.doesGroupClassMatch(newStudent.class_id, newStudent.group_id)) {
            Optional<Student> student = db.createStudent(newStudent);
            student.ifPresent(s -> db.addStudentToGroup(s, newStudent.class_id, newStudent.group_id));
            return student;
        }
        return Optional.empty();
    }

    public Optional<Teacher> createNewTeacher(NewTeacher newTeacher) {
        return db.createTeacher(newTeacher);
    }

}
