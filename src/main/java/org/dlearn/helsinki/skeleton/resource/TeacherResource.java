package org.dlearn.helsinki.skeleton.resource;

import java.util.List;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jersey.repackaged.com.google.common.collect.Lists;
import org.dlearn.helsinki.skeleton.model.NewStudent;
import org.dlearn.helsinki.skeleton.model.NewTeacher;

import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.service.CreateNewStudentService;
import org.dlearn.helsinki.skeleton.service.CreateNewTeacherService;

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
    public TeacherClassResource getClassesFromId(
            @PathParam("teacher_id") int teacher_id) {
        System.out.println("calling classes");
        return new TeacherClassResource();
    }

    CreateNewStudentService createNewStudentService = new CreateNewStudentService();

    @POST
    @Path("/{teacher_id}/create_student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student createNewStudent(@PathParam("teacher_id") int teacher_id,
            NewStudent student) {
        return createNewStudentService.createNewStudent(student);
    }

    CreateNewTeacherService createNewTeacherService = new CreateNewTeacherService();

    @POST
    @Path("/{teacher_id}/create_teacher")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Teacher createNewTeacher(@PathParam("teacher_id") int teacher_id,
            NewTeacher teacher) {
        return createNewTeacherService.createNewTeacher(teacher);
    }

}
