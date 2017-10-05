package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.GroupAnswer;
import org.dlearn.helsinki.skeleton.service.TeacherGroupSurveyAnswerService;

import jersey.repackaged.com.google.common.collect.Lists;

public class TeacherGroupSurveyAnswerResource {
	
	TeacherGroupSurveyAnswerService teacherGroupSurveyAnswerService = new TeacherGroupSurveyAnswerService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GroupAnswer> getAnswersFromGroup(@PathParam("class_id") int class_id, @PathParam("group_id") int group_id, @PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return teacherGroupSurveyAnswerService.getAverageAnswersFromGroup(class_id,group_id,survey_id);
    }
}
