package org.dlearn.helsinki.skeleton.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Survey;

public class SurveyService {

    Database db = new Database();

    public Survey postSurvey(Survey survey) {
        System.out.println("Calling postSurvey");
        try {
            // Posting the new survey with id
            survey = db.postSurvey(survey);
            // fetching all the questions in DB
            List<Question> questions = db.getQuestions();
            // Posting new survey_questions
            db.postSurveyQuestions(questions, survey);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Survey();
    }

    public List<Survey> getSurveysFromClass(int student_id, int class_id) {
        System.out.println("Calling getSurveysFromClass(as Student)");
        try {
            // fetching list of surveys and returning
            return db.getSurveysFromClassAsStudent(student_id, class_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Survey>();
    }

    public List<Survey> getAllSurveys() {
        return db.getSurveys();
    }
}
