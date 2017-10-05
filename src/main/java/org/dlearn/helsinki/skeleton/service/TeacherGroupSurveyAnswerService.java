package org.dlearn.helsinki.skeleton.service;

import java.util.List;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.GroupAnswer;

public class TeacherGroupSurveyAnswerService {
	
	Database db = new Database();

	public List<GroupAnswer> getAverageAnswersFromGroup(int class_id, int group_id, int survey_id) {
		return db.getAverageAnswersFromGroup(class_id,group_id,survey_id);
	}

}
