package org.dlearn.helsinki.skeleton.resource;

import java.util.Optional;
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
    public Optional<StudentClassResource> getGroups(@PathParam("student_id") int student_id) {
        if (SecurityService.hasStudentId(student_id)) {
            return Optional.of(new StudentClassResource());
        }
        return Optional.empty();
    }
    
    @Path("/{student_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<StudentResource> getStudentInfo(@PathParam("student_id") int student_id) {
        if (SecurityService.hasStudentId(student_id)) {
            return Optional.of(new StudentResource());
        }
        return Optional.empty();
    }	
}
