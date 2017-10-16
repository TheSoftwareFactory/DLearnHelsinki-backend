
package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ChangePasswordStudent;
import org.dlearn.helsinki.skeleton.model.Student;

public class ChangePasswordService {
    private static final Database DB = new Database();

    public Student changeStudentPassword(ChangePasswordStudent student) {
        return DB.changeStudentPassword(student)
                .orElse(null);
    }
    
}
