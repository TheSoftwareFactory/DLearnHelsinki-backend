package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.StudentGroup;
import org.dlearn.helsinki.skeleton.service.TeacherGroupService;

public class TeacherGroupResource {
	
	TeacherGroupService teacherGroupService = new TeacherGroupService();

	// simple GET to retrieve all the groups in the class
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StudentGroup> getStudentsInGroups(@PathParam("class_id") int class_id){
		return teacherGroupService.getGroupsWithStudents(class_id);
	}

	// simple GET with group id to retrieve a specific group
	
	// simple GET with group_id and survey to get survey info
    @Path("/{group_id}/surveys")
    public TeacherGroupSurveyResource getSurveyResource(@PathParam("group_id") int group_id) {
    	System.out.println("calling group survey");
    	return new TeacherGroupSurveyResource();
    }
	
}
