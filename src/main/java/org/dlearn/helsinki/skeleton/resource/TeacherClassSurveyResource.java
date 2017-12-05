package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jersey.repackaged.com.google.common.collect.Lists;

import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.SurveyTheme;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.service.TeacherClassSurveyService;

public class TeacherClassSurveyResource {

    TeacherClassSurveyService surveyService = new TeacherClassSurveyService();

    // request teachers/{teacher_id}/surveys/
    // returns all the surveys from teacher based on the teacher_id. a sort of history
    // TODO implement to answer to history request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id) {
        return surveyService.getSurveysFromClassAsTeacher(teacher_id, class_id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public SurveyTheme postSurvey(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id, SurveyTheme surveyTheme) {
        surveyTheme.teacher_id = teacher_id;
        surveyTheme.class_id = class_id;
        System.out.println(surveyTheme.getTitle());
        return surveyService.postSurvey(surveyTheme);
    }

    @POST //(update to close)
    @Path("/{survey_id}")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void closeSurvey(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id) {
        surveyService.closeSurvey(teacher_id, class_id, survey_id);
    }

    @GET
    @Path("/{survey_id}/answers")
    public List<ClassThemeAverage> getClassAverage(
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id) {
        return surveyService.getClassThemeAverage(class_id, survey_id);
    }
}
