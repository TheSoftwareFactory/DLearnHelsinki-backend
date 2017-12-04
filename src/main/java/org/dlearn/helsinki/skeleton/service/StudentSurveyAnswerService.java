package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;

public class StudentSurveyAnswerService {

    Database db = new Database();

    public void putAnswerToQuestion(int student_id, int survey_id,
            int question_id, Answer answer, int class_id) {
        answer.question_id = question_id;
        answer.student_id = student_id;
        answer.survey_id = survey_id;
        db.putAnswerToQuestion(answer, class_id);
    }

    public List<Answer> getAnswers(int student_id, int survey_id) {
        return db.getAnswersFromStudentSurvey(student_id, survey_id);
    }

    public List<StudentThemeAverage> getStudentThemeAverage(int student_id,
            int survey_id) {
        return db.getStudentThemeAverage(survey_id, student_id);
    }

}
