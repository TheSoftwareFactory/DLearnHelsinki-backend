package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.TeacherStudentService;

public class TeacherStudentResource {
	
    static final TeacherStudentService teacherStudentService = new TeacherStudentService();
    
    //@Path("/")
    @GET
    public List<Student> getAllStudentsFromClass(
            @PathParam("class_id") int class_id) {
        System.out.println("fetching students' list from class");
        return teacherStudentService.getAllStudentsFromClass(class_id);
    }

    @Path("/{student_id}")
    @GET
    public Student getStudentFromClass(@PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id,
            @PathParam("teacher_id") int teacher_id) {
        System.out.println("fetching students' list from class");
        return teacherStudentService.getStudent(student_id);
    }

    // TODO implement answer for individual students
    //@Path("/{student_id}/survey/{survey_id}/answers")

}
