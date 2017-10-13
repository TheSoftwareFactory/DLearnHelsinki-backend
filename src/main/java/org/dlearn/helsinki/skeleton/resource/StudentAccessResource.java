package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.SecurityService;
import org.dlearn.helsinki.skeleton.service.StudentService;

@Path("/students")
public class StudentAccessResource {
	static final StudentService studentService = new StudentService();

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

    @Path("/{student_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentInfo(@PathParam("student_id") int student_id) {
        //return new StudentResource();
    	return studentService.getStudent(student_id);
    }
}
