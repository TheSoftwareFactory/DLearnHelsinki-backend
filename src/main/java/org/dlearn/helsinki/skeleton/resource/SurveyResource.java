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

import org.dlearn.helsinki.skeleton.model.SpiderGraph;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.service.SurveyService;

@Path("/")
public class SurveyResource {
	
	SurveyService surveyService = new SurveyService();
	
	
	// request teachers/{teacher_id}/surveys/
	// returns all the surveys from teacher based on the teacher_id. a sort of history
	// TODO implement to answer to history request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("teacher_id") int teacher_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return Lists.newArrayList(
                new Survey(1, "name", 2, "feb 12",
                		"13 feb", teacher_id),
                new Survey(1, "name", 2, "feb 12",
                		"13 feb", teacher_id)
            );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postSurvey(@PathParam("teacher_id") int teacher_id, Survey survey) {
		survey.teacher_id = teacher_id; // need group_id and name from consumer TODO
		surveyService.postSurvey(survey);
    }
}
