package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.StudentGroup;
import org.dlearn.helsinki.skeleton.service.TeacherGroupService;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.GroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.GroupService;
import org.dlearn.helsinki.skeleton.service.ProgressionService;

public class TeacherGroupResource {

    private final TeacherGroupService teacherGroupService = new TeacherGroupService();
    private final GroupService groupService = new GroupService();
    private final ProgressionService progression = new ProgressionService();

    // simple GET to retrieve all the groups in the class
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentGroup> getStudentsInGroups(
            @PathParam("class_id") int class_id) {
        return teacherGroupService.getGroupsWithStudents(class_id);
    }

    // simple GET with group id to retrieve a specific group

    // simple GET with group_id and survey to get survey info
    @Path("/{group_id}/surveys")
    public TeacherGroupSurveyResource getSurveyResource(
            @PathParam("group_id") int group_id) {
        System.out.println("calling group survey");
        return new TeacherGroupSurveyResource();
    }
    
    @GET
    @Path("/{group_id}/progression/{amount}")
    public List<List<GroupThemeAverage>> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id,
            @PathParam("amount") int amount) {
        return progression.getGroupProgression(class_id, group_id,
                amount);
    }

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
    public List<Student> getStudentsFromClassAndGroup(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id) {
        System.out
                .println("fetching all students from the class and the group");
        return groupService.getAllStudentsFromClassAndGroup(class_id, group_id);
    }

}
