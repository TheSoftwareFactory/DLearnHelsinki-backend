package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
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

    private final CreateNewUserService createNewUserService = new CreateNewUserService();
    private final ChangePasswordService change_password = new ChangePasswordService();
    private final SecurityService security = new SecurityService();
    // Request webapi/teachers/
    // Returns the teacher's info based on log credentials
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Teacher getTeacher() {
        return security.getTeacher().orElse(null);
    }

    // TODO delete. reason : function filled by /teachers/ GET
    // Request webapi/teachers/1
    // Returns a teacher based on the id given.
    //@GET
    //@Path("/{teacher_id}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Teacher getTeacherFromId(@PathParam("teacher_id") int teacher_id) {
        return new Teacher(teacher_id, "username " + teacher_id, "password");
    }

    // Request webapi/teachers/1/surveys
    // Returns a teacher based on the id given.
    @Path("/{teacher_id}/classes")
    public TeacherClassResource getClassesFromId(
            @PathParam("teacher_id") int teacher_id) {
    	if(security.isTheTeacher(teacher_id)){
    		return new TeacherClassResource();
    	}else{
    		return null;
    	}
    }

    @POST
    @Path("/{teacher_id}/create_student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student createNewStudent(@PathParam("teacher_id") int teacher_id,
            NewStudent student) {
        try {
            return createNewUserService.createNewStudent(student);
        } catch(StudentExistsException e) {
            throw new WebApplicationException("The student username is invalid or already exists in database. Choose another.", 400);
        }
    }

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
