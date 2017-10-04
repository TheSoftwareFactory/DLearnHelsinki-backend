package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.service.StudentSurveyQuestionService;

public class StudentSurveyQuestionResource {
	
	StudentSurveyQuestionService studentSurveyQuestionService = new StudentSurveyQuestionService();
	
	// Takes a survey_id
	// Returns all the questions of the survey id
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getSurveys(@PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return studentSurveyQuestionService.getSurveyQuestions(survey_id);
    }

}
