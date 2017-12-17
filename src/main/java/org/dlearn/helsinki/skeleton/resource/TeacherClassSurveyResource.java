package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.ThemeAverage;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.SurveyTheme;
import org.dlearn.helsinki.skeleton.service.StudentService;
import org.dlearn.helsinki.skeleton.service.TeacherClassSurveyService;

public class TeacherClassSurveyResource {

    TeacherClassSurveyService surveyService = new TeacherClassSurveyService();
    StudentService studentService = new StudentService();

    // TODO implement to answer to history request
    /**
     * request teachers/{teacher_id}/surveys/
     * @param teacher_id
     * @param class_id
     * @return all the surveys from teacher based on the teacher_id. a sort of history
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id) {
        return surveyService.getSurveysFromClassAsTeacher(teacher_id, class_id);
    }

    /**
     * Creating a new survey
     * @param teacher_id
     * @param class_id
     * @param surveyTheme
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public SurveyTheme postSurvey(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id, SurveyTheme surveyTheme) {
        surveyTheme.teacher_id = teacher_id;
        surveyTheme.class_id = class_id;
        System.out.println(surveyTheme.getTitle());
        return surveyService.postSurvey(surveyTheme);
    }

    /**
     * Close survey
     * @param teacher_id
     * @param class_id
     * @param survey_id 
     */
    @POST //(update to close)
    @Path("/{survey_id}")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void closeSurvey(@PathParam("teacher_id") int teacher_id,
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id) {
        surveyService.closeSurvey(teacher_id, class_id, survey_id);
    }

    /**
     * get average for a class
     * @param class_id
     * @param survey_id
     * @return 
     */
    @GET
    @Path("/{survey_id}/answers")
    public List<ThemeAverage> getClassAverage(
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id) {
        return surveyService.getClassThemeAverage(class_id, survey_id);
    }

    /**
    * request teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/class_averages
    * @param survey_id
    * @param class_id
    * @return average values of one survey in all the groups of one class
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/class_averages")
    public List<ThemeAverage> getSurveyClassAverages(
            @PathParam("survey_id") int survey_id,
            @PathParam("class_id") int class_id) {
        return studentService.getSurveyAnswerAverages(0, class_id, 0,
                survey_id);
    }

    /**
     * request teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/group_averages
     * @param survey_id 
     * @param class_id
     * @param group_id, given in ?group_id={group_id}
     * @return average values of one survey in all the groups of one class
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/group_averages")
    public List<ThemeAverage> getSurveyGroupAverages(
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id,
            @QueryParam("group_id") int group_id) {
        return studentService.getSurveyAnswerAverages(0, class_id, group_id,
                survey_id);
    }
}
