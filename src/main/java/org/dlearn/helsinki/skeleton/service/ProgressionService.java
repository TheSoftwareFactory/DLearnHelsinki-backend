
package org.dlearn.helsinki.skeleton.service;

import java.util.List;
import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.ListThemeAverage;

public class ProgressionService {
    private static final Database DB = new Database();

    public List<ListThemeAverage> getStudentProgression(int student_id,
            int amount) {
        return DB.getStudentThemeAverageProgression(student_id, 0, amount);
    }

    public List<ListThemeAverage> getStudentClassProgression(int class_id,
            int student_id, int amount) {
        return DB.getStudentThemeAverageProgression(student_id, class_id,
                amount);
    }

    public List<ListThemeAverage> getGroupProgression(int class_id,
            int group_id, int amount) {
        return DB.getGroupThemeAverageProgression(class_id, group_id, amount);
    }

    public List<ListThemeAverage> getClassProgression(int class_id,
            int amount) {
        return DB.getGroupThemeAverageProgression(class_id, 0, amount);
    }
}
