package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.service.AnswerService;

public class AnswerResource {

    AnswerService answerService = new AnswerService();

    /**
     * WIP Service not implemented
     * @param survey_id
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getStudentAnswers(
            @PathParam("survey_id") int survey_id) {
        return answerService.getAnswers(survey_id);
    }
}
