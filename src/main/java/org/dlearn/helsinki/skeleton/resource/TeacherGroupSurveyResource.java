package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class TeacherGroupSurveyResource {

	// simple GET with group_id and survey to get survey info
    @Path("/{survey_id}/answers")
    public TeacherGroupSurveyAnswerResource getGroupResource(@PathParam("group_id") int group_id) {
    	System.out.println("calling group answers");
    	return new TeacherGroupSurveyAnswerResource();
    }
}
