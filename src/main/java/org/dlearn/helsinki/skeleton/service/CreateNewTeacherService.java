package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Teacher;

public class CreateNewTeacherService {

    private final Database db = new Database();

    public Teacher createNewTeacher(NewTeacher newTeacher) {
        // TODO: Check that password isn't too short.
        return db.createTeacher(newTeacher);
    }

}
