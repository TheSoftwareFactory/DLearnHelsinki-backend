package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.SurveyService;

import jersey.repackaged.com.google.common.collect.Lists;

public class StudentSurveyResource {

	SurveyService surveyService = new SurveyService();
	
	
	// request students/{student_id}/classes/{student_id}/surveys/
	// returns all the surveys from teacher based on the student_id. a sort of history
	// TODO implement to answer to history request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("student_id") int student_id, @PathParam("class_id") int class_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return surveyService.getSurveysFromClass(student_id,class_id);
    }
    
    @Path("/{survey_id}/questions")
    public StudentSurveyQuestionResource getSurveyQuestions(@PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return new StudentSurveyQuestionResource();
    }
	/*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postSurvey(@PathParam("teacher_id") int teacher_id, @PathParam("class_id") int class_id, Survey survey) {
		survey.teacher_id = teacher_id; 
		survey.class_id = class_id;
		System.out.println(survey.getTitle());
		surveyService.postSurvey(survey);
    }
    */
}
