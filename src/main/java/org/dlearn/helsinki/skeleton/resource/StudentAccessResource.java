package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.dlearn.helsinki.skeleton.service.SecurityService;

@Path("/students")
public class StudentAccessResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkLogin() {
        return "logged in";
    }

    @Path("/{student_id}/classes")
    public StudentClassResource getGroups(
            @PathParam("student_id") int student_id) {
        return new StudentClassResource();
    }

    @Path("/{studentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StudentResource getStudentInfo() {
        return new StudentResource();
    }
}
