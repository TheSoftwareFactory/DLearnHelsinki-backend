package org.dlearn.helsinki.skeleton.service;

import java.util.List;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Survey;

public class SurveyService {
    private final Database db = new Database();

    public Survey getSurvey(int survey_id) {
        return db.getSurvey(survey_id);
    }

    public List<Survey> getAllSurveys() {
        return db.getSurveys();
    }
}
