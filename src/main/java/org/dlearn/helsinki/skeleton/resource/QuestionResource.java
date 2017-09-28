package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.AnswerQuestionService;

public class QuestionResource {
	
	AnswerQuestionService answerQuestionService = new AnswerQuestionService();

	// TODO get last survey on db
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getSurvey(@PathParam("survey_id") int survey_id) {
    	System.out.println("fetching survey questions");
        return answerQuestionService.getSurveyQuestions(survey_id);
    }
}
