package org.dlearn.helsinki.skeleton.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.Survey;

public class StudentClassSurveyService {
	
	final static Database db = new Database();

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
}
