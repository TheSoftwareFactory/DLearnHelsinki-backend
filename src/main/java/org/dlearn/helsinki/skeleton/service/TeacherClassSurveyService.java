
package org.dlearn.helsinki.skeleton.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.SurveyTheme;
import org.dlearn.helsinki.skeleton.model.ThemeAverage;

public class TeacherClassSurveyService {

    Database db = new Database();

    public SurveyTheme postSurvey(SurveyTheme surveyTheme) {
        System.out.println("Calling postSurvey");
        try {
            // Posting the new survey with id
            surveyTheme = db.postSurvey(surveyTheme);
            // fetching all the questions in DB
            List<Question> questions = db.getQuestions(surveyTheme);
            // Posting new survey_questions
            db.postSurveyQuestions(questions, surveyTheme);
            surveyTheme.open = true;
            return surveyTheme;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new SurveyTheme();
    }

    ///*
    //*/

    public List<Survey> getSurveysFromClassAsTeacher(int teacher_id,
            int class_id) {
        System.out.println("Calling getSurveysFromClass(as Teacher)");
        try {
            // fetching list of surveys and returning
            return db.getSurveysFromClassAsTeacher(teacher_id, class_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Survey>();
    }

    public List<ThemeAverage> getClassThemeAverage(int class_id,
            int survey_id) {
        return db.getSurveyAnswerAverages(0, class_id, 0, survey_id);
    }

    public void closeSurvey(int teacher_id, int class_id, int survey_id) {
        db.closeSurvey(teacher_id, class_id, survey_id);

    }
}
