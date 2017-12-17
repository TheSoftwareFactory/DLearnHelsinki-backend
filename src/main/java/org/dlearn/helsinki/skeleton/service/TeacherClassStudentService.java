package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ThemeAverage;

public class TeacherClassStudentService {

    private static final Database DB = new Database();

    public List<ThemeAverage> getStudentThemeAverage(int survey_id,
            int student_id) {
        return DB.getSurveyAnswerAverages(student_id, 0, 0, survey_id);
    }

}
