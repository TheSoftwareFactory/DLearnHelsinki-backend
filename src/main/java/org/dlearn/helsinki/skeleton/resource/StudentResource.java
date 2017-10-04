package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jersey.repackaged.com.google.common.collect.Lists;

import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.GroupService;
import org.dlearn.helsinki.skeleton.service.StudentService;


public class StudentResource {
	static final StudentService studentService = new StudentService(); 	
	@GET
	@Path("/students")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudents(@PathParam("student_id") int student_id) {
        return Lists.newArrayList(
            new Student(student_id, "username", "password", null, student_id),
            new Student(student_id + 1, "username2", "password2", null, student_id)
        );
    }
	
	@GET
	@Path("/student/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentInfo(@PathParam("studentId") int student_id) {
		return studentService.getStudent(student_id);
    }	
}
