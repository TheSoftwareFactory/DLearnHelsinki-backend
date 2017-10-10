package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Answer;

public class StudentSurveyAnswerService {

    Database db = new Database();

    public void putAnswerToQuestion(int student_id, int survey_id,
            int question_id, Answer answer) {
        answer.question_id = question_id;
        answer.student_id = student_id;
        answer.survey_id = survey_id;
        db.putAnswerToQuestion(answer);
    }

    public List<Answer> getAnswers(int student_id, int survey_id) {
        // TODO Auto-generated method stub
        return db.getAnswersFromStudentSurvey(student_id, survey_id);
    }

}
