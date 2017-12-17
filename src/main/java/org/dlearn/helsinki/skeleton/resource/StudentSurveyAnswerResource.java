package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.model.ThemeAverage;
import org.dlearn.helsinki.skeleton.service.StudentSurveyAnswerService;

public class StudentSurveyAnswerResource {

    StudentSurveyAnswerService studentSurveyAnswerService = new StudentSurveyAnswerService();

    /**
     * Add a single answer to database
     * @param student_id
     * @param class_id
     * @param survey_id
     * @param answer_id
     * @param answer 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{answer_id}")
    public void putSurveyAnswers(@PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("answer_id") int answer_id, Answer answer) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        System.out.println("Student Answering a question");
        studentSurveyAnswerService.putAnswerToQuestion(student_id, survey_id,
                answer_id, answer, class_id);
    }

    /**
     * Add all answers of a given survey to database
     * @param student_id
     * @param class_id
     * @param survey_id
     * @param answers
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean postAnswers(@PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id, List<Answer> answers) {

        System.out.println("Student Answering a survey");
        System.out.println("Class_id: " + class_id);
        System.out.println("Stuident_id: " + student_id);
        System.out.println("survey_id" + survey_id);
        System.out.println(answers);
        return studentSurveyAnswerService.postAnswersToQuestion(class_id,
                student_id, survey_id, answers);
    }

    /**
     * Get answers by a student to a given survey
     * @param student_id
     * @param survey_id
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ThemeAverage> getSurveyAnswers(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return studentSurveyAnswerService.getStudentThemeAverage(student_id,
                survey_id);
    }

}
