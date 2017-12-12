package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;

public class AnswerQuestionService {

    Database db = new Database();

    /**
     * Gets questions for a survey
     * @param survey_id
     * @return questions
     */
    public List<Question> getSurveyQuestions(int survey_id) {
        // TODO Auto-generated method stub
        return db.getQuestionsFromSurvey(survey_id);
    }

}
