package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.dlearn.helsinki.skeleton.model.StudentGroup;
import org.dlearn.helsinki.skeleton.service.TeacherGroupService;
import org.dlearn.helsinki.skeleton.exceptions.GroupCannotBeClosedException;
import org.dlearn.helsinki.skeleton.exceptions.GroupUpdateUnsuccessful;
import org.dlearn.helsinki.skeleton.exceptions.InvalidAgeException;
import org.dlearn.helsinki.skeleton.exceptions.PasswordException;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.ListGroupThemeAverage;
import org.dlearn.helsinki.skeleton.model.NewStudentGroup;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.ProgressionService;

public class TeacherClassGroupResource {

    private final TeacherGroupService teacherGroupService = new TeacherGroupService();
    private final ProgressionService progression = new ProgressionService();

    // Simple GET to retrieve all the groups in the class and students in them.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentGroup> getStudentsInGroups(
            @PathParam("class_id") int class_id,
            @QueryParam("all") boolean all) {
        return teacherGroupService.getGroupsWithStudents(class_id, all);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StudentGroup ginsertGroupInClass(@PathParam("class_id") int class_id,
            NewStudentGroup group)
            throws RuntimeException, PasswordException, InvalidAgeException {
        return teacherGroupService.insertNewGroupInClass(class_id, group);
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListGroupThemeAverage> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id,
            @PathParam("amount") int amount) {
        return progression.getGroupProgression(class_id, group_id, amount);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Group getGroupFromClass(@PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id) {
        System.out.println("fetching a group from the class");
        return teacherGroupService.getGroupFromClass(class_id, group_id);
    }

    @Path("/{group_id}")
    @DELETE
    public void deleteGroupFromClass(@PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id) {
        System.out.println("deleting a group from the class");
        try {
            teacherGroupService.deleteGroupFromClass(class_id, group_id);
        } catch (GroupCannotBeClosedException e) {
            throw new WebApplicationException(
                    Response.status(Status.METHOD_NOT_ALLOWED)
                            .entity("Group cannot be closed. It contains students.")
                            .build());
        }
    }

    @Path("/{group_id}")
    @PUT // Quite frankly it should be UPDATE and not PUT.
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateGroupInClass(@PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id, Group group) {
        // TODO check the teacher has access to this group and class
        try {
            teacherGroupService.updateGroupInClass(class_id, group_id, group);
        } catch (GroupUpdateUnsuccessful e) {
            throw new WebApplicationException(Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Could not update the group in database.").build());
        }
    }

    @Path("/{group_id}/students")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudentsFromClassAndGroup(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id) {
        System.out
                .println("fetching all students from the class and the group");
        return teacherGroupService.getAllStudentsFromClassAndGroup(class_id,
                group_id);
    }

}
