package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.SecurityService;

@Path("/students")
public class StudentAccessResource {

	private final SecurityService security = new SecurityService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student checkLogin() {
    	return security.getStudent().orElse(null);
    }

    @Path("/{student_id}/classes")
    public StudentClassResource getGroups(
            @PathParam("student_id") int student_id) {
    	if(security.isTheStudent(student_id)){
    		return new StudentClassResource();
    	}else{
    		return null;
    	}
    }

    @Path("/{studentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StudentResource getStudentInfo() {
        return new StudentResource();
    }
}
