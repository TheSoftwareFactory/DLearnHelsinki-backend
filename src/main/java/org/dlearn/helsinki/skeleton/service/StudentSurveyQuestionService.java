package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;

public class StudentSurveyQuestionService {

    Database db = new Database();

    public List<Question> getSurveyQuestions(int survey_id) {
        return db.getQuestionsFromSurvey(survey_id);
    }

}
