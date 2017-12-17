package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.ThemeAverage;

public class TeacherGroupSurveyService {

    Database db = new Database();

    public List<ThemeAverage> getAverageAnswersFromGroup(int class_id,
            int group_id, int survey_id) {
        return db.getSurveyAnswerAverages(0, class_id, group_id, survey_id);
    }

    public List<Question> getQuestionsFromSurvey(int survey_id) {
        return db.getQuestionsFromSurvey(survey_id);
    }

}
