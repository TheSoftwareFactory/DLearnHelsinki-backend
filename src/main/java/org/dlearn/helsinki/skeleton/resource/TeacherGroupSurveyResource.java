package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.GroupAnswer;
import org.dlearn.helsinki.skeleton.model.GroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.Question;
import org.dlearn.helsinki.skeleton.service.TeacherGroupSurveyService;

public class TeacherGroupSurveyResource {

    TeacherGroupSurveyService teacherGroupSurveyAnswerService = new TeacherGroupSurveyService();
    
    // simple GET with group_id and survey to get survey info
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/answers")
    public List<GroupThemeAverage> getAnswersFromSurvey(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id,
            @PathParam("survey_id") int survey_id) {
        System.out.println("calling group answers");
        return teacherGroupSurveyAnswerService
                .getAverageAnswersFromGroup(class_id, group_id, survey_id);
    }

    // simple GET with group_id and survey to get survey info
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{survey_id}/questions")
    public List<Question> getGroupResource(
            @PathParam("survey_id") int survey_id) {
        System.out.println("calling group questions");
        return teacherGroupSurveyAnswerService
                .getQuestionsFromSurvey(survey_id);
    }
}
