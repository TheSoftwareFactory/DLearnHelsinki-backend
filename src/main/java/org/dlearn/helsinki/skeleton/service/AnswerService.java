package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Answer;

public class AnswerService {
	
	Database db = new Database();
	
	public List<Answer> getAnswers(int survey_id) {
		// TODO implement getAnswers to fetch all the student's answers to the particular survey
		return null;
	}

	public void postAnswers(List<Answer> answers, int survey_id) {
		// TODO implement postAnswers to post all the student's answers to the particular survey
		System.out.println("asnwers : " + answers);
		System.out.println("survey_id : " + survey_id);
		// TODO replace student_id with real student id
		return db.postStudentAnswersForSurvey(answers, survey_id, 1);
	}

}
