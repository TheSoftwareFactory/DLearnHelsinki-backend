package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jersey.repackaged.com.google.common.collect.Lists;

import org.dlearn.helsinki.skeleton.exceptions.StudentExistsException;
import org.dlearn.helsinki.skeleton.model.ChangePasswordStudent;
import org.dlearn.helsinki.skeleton.model.NewStudent;

import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.service.ChangePasswordService;
import org.dlearn.helsinki.skeleton.service.CreateNewUserService;
import org.dlearn.helsinki.skeleton.service.SecurityService;

@Path("/teachers")
public class TeacherResource {

    // Request webapi/teachers/
    // Returns all teachers present in the db.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Teacher> getTeachers() {
        return Lists.newArrayList(new Teacher(1, "username2", "password"),
                new Teacher(2, "username1", "password"));
    }

    // Request webapi/teachers/1
    // Returns a teacher based on the id given.
    @GET
    @Path("/{teacher_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Teacher getTeacherFromId(@PathParam("teacher_id") int teacher_id) {
        return new Teacher(teacher_id, "username " + teacher_id, "password");
    }

    // Request webapi/teachers/1/surveys
    // Returns a teacher based on the id given.
    @Path("/{teacher_id}/classes")
    public TeacherClassResource getClassesFromId(@PathParam("teacher_id") int teacher_id) {
    	System.out.println("calling teacher classes resource");
        return new TeacherClassResource();
    }

    private final CreateNewUserService createNewUserService = new CreateNewUserService();

    @POST
    @Path("/{teacher_id}/create_student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student createNewStudent(@PathParam("teacher_id") int teacher_id, NewStudent student) {
    	try {
            return createNewUserService.createNewStudent(student);
    	} catch(StudentExistsException e) {
            throw new WebApplicationException("The student username is invalid or already exists in database. Choose another.", 400);
    	}
    }

    private final ChangePasswordService change_password = new ChangePasswordService();
    private final SecurityService security = new SecurityService();

    @POST
    @Path("/{teacher_id}/change_student_password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student changeStudentPassword(@PathParam("teacher_id") int teacher_id, ChangePasswordStudent student) {
        return security.getTeacher()
            .map(t -> change_password.changeStudentPassword(student))
            .orElse(null);
    }
}
