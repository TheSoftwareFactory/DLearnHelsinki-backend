package org.dlearn.helsinki.skeleton.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.mentor.LocalOutlierFactor;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentLof;

public class OutlierService {

    static final Database db = new Database();

    /* Service that binds database and outlier detection together
       Current version has fixed minPts argument
    */
    public List<StudentLof> getClassOutliers(int class_id) {
        // STATIC MINPTS FOR NOW
        int minPts = 1;
        LocalOutlierFactor lof = new LocalOutlierFactor();
        List<Answer> answers = db.getClassAnswers(class_id);
        Map<Integer, Double> outliers = lof.outliers(minPts, answers);
        // If there are no answers in any of the class surveys
        if (outliers.isEmpty())
            return null;
        List<StudentLof> outlierResponse = new ArrayList();
        for (Map.Entry<Integer, Double> entry : outliers.entrySet()) {
            int student_id = entry.getKey();
            Student student = db.getStudent(student_id);
            StudentLof stdlof = new StudentLof(student);
            double lof_score = entry.getValue();
            stdlof.setLofScore(lof_score);
            outlierResponse.add(stdlof);
        }
        return outlierResponse;
    }

}
