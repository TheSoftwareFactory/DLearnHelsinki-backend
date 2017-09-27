package org.dlearn.helsinki.skeleton.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Survey;

public class GroupSurveyResource {

	// TODO get last survey on db
	@Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Survey getSurvey() {
        return new Survey();
    }
    
    // TODO implement same as getSurvey()
    @Path("/{survey_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Survey getSurveyInfo(@PathParam("survey_id") int survey_id) {
    	return new Survey();
    }
    
    @Path("/{survey_id}/questions")
    @GET
    public AnswerQuestionResource getQuestions(@PathParam("survey_id") int survey_id) {
    	return new AnswerQuestionResource();
    }
}
