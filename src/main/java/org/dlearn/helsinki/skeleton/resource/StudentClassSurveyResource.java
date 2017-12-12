package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.StudentClassSurveyService;
import org.dlearn.helsinki.skeleton.service.StudentService;

public class StudentClassSurveyResource {

    StudentService studentService = new StudentService();
    StudentClassSurveyService surveyService = new StudentClassSurveyService();

    /**
     * request students/{student_id}/classes/{class_id}/surveys/
     * TODO implement to answer to history request
     * @param student_id
     * @param class_id
     * @return all the surveys from teacher based on the student_id. a sort of history
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return surveyService.getSurveysFromClass(student_id, class_id);
    }

    /**
     * 
     * @param student_id
     * @param survey_id
     * @param class_id
     * @return average values of one survey in all the groups of one class
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/class_averages")
    public List<StudentThemeAverage> getSurveyClassAverages(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("class_id") int class_id) {
        return studentService.getSurveyAnswerAverages(0, class_id, 0,
                survey_id);
    }

    /**
     * 
     * @param student_id, required to check which group averages are looked for
     * @param survey_id 
     * @param class_id, required to check which group averages are looked for
     * @return average values of one survey in all the groups of one class
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/group_averages")
    public List<StudentThemeAverage> getSurveyGroupAverages(
            @PathParam("student_id") int student_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("class_id") int class_id) {
        return studentService.getGroupSurveyAnswerAverages(student_id, class_id,
                survey_id);
    }

    /**
     * Used to get survey questions
     * @param survey_id
     * @return 
     */
    @Path("/{survey_id}/questions")
    public StudentSurveyQuestionResource getSurveyQuestions(
            @PathParam("survey_id") int survey_id) {
        return new StudentSurveyQuestionResource();
    }

    /**
     * 
     * @param survey_id
     * @return 
     */
    @Path("/{survey_id}/answers")
    public StudentSurveyAnswerResource getSurveyAnswers(
            @PathParam("survey_id") int survey_id) {
        return new StudentSurveyAnswerResource();
    }
}
