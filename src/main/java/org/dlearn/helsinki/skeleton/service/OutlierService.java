package org.dlearn.helsinki.skeleton.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.mentor.LocalOutlierFactor;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentLof;
import org.dlearn.helsinki.skeleton.model.Question;

public class OutlierService {

    static final Database db = new Database();

    /* Service that binds database and outlier detection together
       Current version has fixed minPts argument
    */
    public List<StudentLof> getClassOutliers(int class_id) {

        List<StudentLof> outlierResponse = new ArrayList();
        // STATIC MINPTS FOR NOW
        int amountOfQuestions = 0;
        int minPts = 1;
        List<Answer> answers = db.getClassAnswers(class_id);
        List<Integer> surveys = new ArrayList();
        LocalOutlierFactor lof = new LocalOutlierFactor();
        for (Answer ans : answers) {
            if (!surveys.contains(ans.getSurvey_id()))
                surveys.add(ans.getSurvey_id());
        }
        // Calculates how many questions a class has in total
        // i.e. sum all questions in all surveys of a class
        for (Integer survey : surveys) {
            List<Question> questions = db.getQuestionsFromSurvey(survey);
            amountOfQuestions += questions.size();
        }
        Map<Integer, Double> outliers = lof.outliers(minPts, amountOfQuestions,
                answers);

        // If there are no answers in any of the class surveys
        if (outliers.isEmpty())
            return outlierResponse;

        for (Map.Entry<Integer, Double> entry : outliers.entrySet()) {
            int student_id = entry.getKey();
            Student student = db.getStudent(student_id);
            StudentLof stdlof = new StudentLof(student);
            double lof_score = entry.getValue();
            stdlof.setClass_id(class_id);
            stdlof.setLofScore(lof_score);
            outlierResponse.add(stdlof);
        }
        return outlierResponse;
    }

}
