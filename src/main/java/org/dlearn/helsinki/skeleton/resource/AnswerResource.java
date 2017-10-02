package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.service.AnswerService;

public class AnswerResource {
	
	AnswerService answerService = new AnswerService();

	// GET all answers
	// TODO implement method in AnswerService
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getStudentAnswers(@PathParam("survey_id") int survey_id) {
    	System.out.println("fetching survey questions");
        return answerService.getAnswers(survey_id);
    }
	// POST all the answers
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postStudentAnswers(@PathParam("survey_id") int survey_id, List<Answer> answers) {
    	System.out.println("fetching survey questions");
        answerService.postAnswers(answers,survey_id);
        return "";
    }
	// GET one answer
	// POST one answer
}
