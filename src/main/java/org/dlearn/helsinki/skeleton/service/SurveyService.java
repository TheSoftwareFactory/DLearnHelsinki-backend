package org.dlearn.helsinki.skeleton.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.SpiderGraph;
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
        return new Survey(-1,"name",-1,"","",-1);
	}
}
