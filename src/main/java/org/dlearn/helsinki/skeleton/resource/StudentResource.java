package org.dlearn.helsinki.skeleton.resource;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import jersey.repackaged.com.google.common.collect.Lists;

import org.dlearn.helsinki.skeleton.model.Student;
import static org.eclipse.persistence.config.TargetDatabase.Database;

@Path("/students")
public class StudentResource {

    @GET
    @RolesAllowed("teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudents(@PathParam("student_id") int student_id) {
        return Lists.newArrayList(
            new Student(student_id, "lastname", "firstname", "username", "password"),
            new Student(student_id + 1, "lastname2", "firstname2", "username2", "password2")
        );
    }

    @GET
    @RolesAllowed("teacher")
    @Path("/{student_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentFromId(@PathParam("student_id") int student_id) {
        return new Student(student_id, "lastname", "firstname", "username", "password");
    }

    @Path("/{student_id}/spidergraphs")
    public SpiderGraphResource getSpiderGraphResource(@Context SecurityContext sec, @PathParam("student_id") int student_id) {
        Principal principal = sec.getUserPrincipal();
        if (principal == null) {
            return null;
        }
        if (sec.isUserInRole("teacher") || principal.getName().equals(getStudentFromId(student_id).username)) {
            return new SpiderGraphResource();
        } else {
             return null;
        }
    }
}
