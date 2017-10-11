package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.service.StudentSurveyAnswerService;
import org.dlearn.helsinki.skeleton.service.StudentSurveyQuestionService;

public class StudentSurveyAnswerResource {

    StudentSurveyAnswerService studentSurveyAnswerService = new StudentSurveyAnswerService();

    // returns all the surveys from teacher based on the student_id. a sort of history
    // TODO implement to answer to history request.

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{answer_id}")
    public void putSurveyAnswers(@PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("answer_id") int answer_id, Answer answer) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        studentSurveyAnswerService.putAnswerToQuestion(student_id, survey_id,
                answer_id, answer);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentThemeAverage> getSurveyAnswers(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return studentSurveyAnswerService.getStudentThemeAverage(student_id,
                survey_id);
    }

}
