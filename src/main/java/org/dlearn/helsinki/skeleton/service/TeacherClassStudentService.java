package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;

public class TeacherClassStudentService {

    private static final Database DB = new Database();

    public List<StudentThemeAverage> getStudentThemeAverage(int survey_id,
            int student_id) {
        return DB.getStudentThemeAverage(survey_id, student_id);
    }

}
