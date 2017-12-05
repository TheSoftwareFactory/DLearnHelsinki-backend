package org.dlearn.helsinki.skeleton.resource;

import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.AnswersAvgs;
import org.dlearn.helsinki.skeleton.model.ListStudentThemeAverage;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.service.ProgressionService;
import org.dlearn.helsinki.skeleton.service.SecurityService;
import org.dlearn.helsinki.skeleton.service.StudentService;

@Path("/students")
public class StudentAccessResource {

    private final StudentService studentService = new StudentService();
    private final SecurityService security = new SecurityService();
    private final ProgressionService progression = new ProgressionService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student checkLogin() {
        System.out.println("calling checkLogin");
        return security.getStudent().orElse(null);
    }

    @GET
    @Path("/{student_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListStudentThemeAverage> getProgression(
            @PathParam("amount") int amount) {
        return security.getStudent()
                .map(s -> progression.getStudentProgression(s._id, amount))
                .orElse(Collections.EMPTY_LIST);
    }

    // returns averages from all surveys from all students classes
    @GET
    @Path("/{student_id}/survey_averages")
    @Produces(MediaType.APPLICATION_JSON)
    public AnswersAvgs getSurveyAverages(
            @PathParam("student_id") int student_id) {
        return studentService.getSurveyAnswerAverages(student_id, 0, 0, 0);
    }

    @Path("/{student_id}/classes")
    public StudentClassResource getClasses(
            @PathParam("student_id") int student_id) {
        System.out.println("calling students/classes");
        if (security.isTheStudent(student_id)) {
            return new StudentClassResource();
        } else {
            return null;
        }
    }

    @GET
    @Path("/{student_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentInfo(@PathParam("student_id") int student_id) {
        //return new StudentResource();
        return studentService.getStudent(student_id);
    }
}
