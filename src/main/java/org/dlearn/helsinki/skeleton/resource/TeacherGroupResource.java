package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.GroupService;

public class TeacherGroupResource {
	final static GroupService groupService = new GroupService();
	
    //@Path("/")
    /*
	@GET
    public List<Group> getAllGroupsFromClass(@PathParam("class_id") int class_id) {
    	System.out.println("fetching all groups from the class");
    	return groupService.getAllGroupsFromClass(class_id);
    }
    */
    
    @Path("/{group_id}")
    @GET
    public Group getGroupFromClass(@PathParam("class_id") int class_id, 
    							   @PathParam("group_id") int group_id) {
    	System.out.println("fetching a group from the class");
    	return groupService.getGroupFromClass(class_id, group_id);
    }
    
    @Path("/{group_id}/students")
    @GET
    public List<Student> getStudentsFromClassAndGroup(@PathParam("class_id") int class_id, 
    							   @PathParam("group_id") int group_id) {
    	System.out.println("fetching all students from the class and the group");
    	return groupService.getAllStudentsFromClassAndGroup(class_id, group_id);
    }
}
