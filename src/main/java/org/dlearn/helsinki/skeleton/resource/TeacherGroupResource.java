package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class TeacherGroupResource {

	// simple GET to retrieve all the groups in the class

	// simple GET with group id to retrieve a specific group
	
	// simple GET with group_id and survey to get survey info
    @Path("/{group_id}/surveys")
    public TeacherGroupSurveyResource getSurveyResource(@PathParam("group_id") int group_id) {
    	System.out.println("calling group survey");
    	return new TeacherGroupSurveyResource();
    }
	
}
