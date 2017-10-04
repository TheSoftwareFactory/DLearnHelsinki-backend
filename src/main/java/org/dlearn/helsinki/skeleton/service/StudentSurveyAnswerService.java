package org.dlearn.helsinki.skeleton.service;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Answer;

public class StudentSurveyAnswerService {
	
	Database db = new Database();

	public void putAnswerToQuestion(int student_id, int survey_id, int question_id, Answer answer) {
		answer.questionnaire_id = question_id;
		answer.student_id = student_id;
		answer.survey_id = survey_id;
		db.putAnswerToQuestion(answer);
	}

}
