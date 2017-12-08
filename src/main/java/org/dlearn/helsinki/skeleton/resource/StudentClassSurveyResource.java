package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.AnswersAvgs;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.StudentClassSurveyService;
import org.dlearn.helsinki.skeleton.service.StudentService;

public class StudentClassSurveyResource {

    // TODO make a new service?
    StudentService studentService = new StudentService();
    StudentClassSurveyService surveyService = new StudentClassSurveyService();

    // request students/{student_id}/classes/{class_id}/surveys/
    // returns all the surveys from teacher based on the student_id. a sort of history
    // TODO implement to answer to history request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return surveyService.getSurveysFromClass(student_id, class_id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/class_averages")
    public AnswersAvgs getSurveyClassAverages(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("class_id") int class_id) {
        // returns average values of one survey in all the groups of one class
        return studentService.getSurveyAnswerAverages(0, class_id, 0,
                survey_id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/group_averages")
    public AnswersAvgs getSurveyGroupAverages(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("class_id") int class_id) {
        // returns average values of one survey in all the groups of one class
        // student and class are required to check which group averages are looked for.
        return studentService.getGroupSurveyAnswerAverages(student_id, class_id,
                survey_id);
    }

    @Path("/{survey_id}/questions")
    public StudentSurveyQuestionResource getSurveyQuestions(
            @PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return new StudentSurveyQuestionResource();
    }

    @Path("/{survey_id}/answers")
    public StudentSurveyAnswerResource getSurveyAnswers(
            @PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return new StudentSurveyAnswerResource();
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
