package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.SurveyService;

public class GroupSurveyResource {

    SurveyService surveyService = new SurveyService();

    // TODO get last survey on db
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Survey getSurvey() {
        return new Survey(1, null, null, null, null, null, null, 1, 0, false);
    }

    // TODO implement same as getSurvey()
    @Path("/{survey_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Survey getSurveyInfo(@PathParam("survey_id") int survey_id) {
        return surveyService.getSurvey(survey_id);
    }

    @Path("/{survey_id}/questions")
    public QuestionResource getQuestions(
            @PathParam("survey_id") int survey_id) {
        return new QuestionResource();
    }

    @Path("/{survey_id}/answers")
    public AnswerResource getAnswers(@PathParam("survey_id") int survey_id) {
        return new AnswerResource();
    }
}
