package org.dlearn.helsinki.skeleton.resource;

import java.util.Collections;
import java.util.List;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.dlearn.helsinki.skeleton.model.Answer;

import org.dlearn.helsinki.skeleton.model.AnswersAvgs;
import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.service.ClassService;
import org.dlearn.helsinki.skeleton.model.ListStudentThemeAverage;
import org.dlearn.helsinki.skeleton.service.ProgressionService;
import org.dlearn.helsinki.skeleton.service.SecurityService;
import org.dlearn.helsinki.skeleton.service.StudentService;
import org.dlearn.helsinki.skeleton.service.StudentSurveyAnswerService;

// Called by StudentAccess Students/1/classes
public class StudentClassResource {

    private final ClassService classService = new ClassService();
    private final SecurityService security = new SecurityService();
    private final ProgressionService progression = new ProgressionService();
    private final StudentService studentService = new StudentService();
    private final StudentSurveyAnswerService studentSurveyAnswerService = new StudentSurveyAnswerService();

    // request students/{student_id}/classes
    // returns the students classes based on the student_id.
    // TODO Does not work!!!!! -Pascal (probably the sets in the db method)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Classes> getAllClassesStundentIsIn(
            @PathParam("student_id") int student_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return classService.getAllClassesStundentIsIn(student_id);
    }

    @Path("/{class_id}/surveys")
    public StudentClassSurveyResource getStudentSurveyResource(
            @PathParam("class_id") int class_id) {
        System.out.println("calling classes/surveys");
        return new StudentClassSurveyResource();
    }

    @GET
    @Path("/{class_id}/group_averages")
    @Produces(MediaType.APPLICATION_JSON)
    public AnswersAvgs getStudentGroupAverage(
            @PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id) {
        return studentService.getGroupAnswerAverages(student_id, class_id);
    }

    @GET
    @Path("/{class_id}/class_averages")
    @Produces(MediaType.APPLICATION_JSON)
    public AnswersAvgs getStudentClassAverage(
            @PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id) {
        return studentService.getSurveyAnswerAverages(0, class_id, 0, 0);
    }

    @GET
    @Path("/{class_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListStudentThemeAverage> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("amount") int amount) {
        return security
                .getStudent().map(s -> progression
                        .getStudentClassProgression(class_id, s._id, amount))
                .orElse(Collections.EMPTY_LIST);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{class_id}/answers")
    public boolean post_answers(@PathParam("student_id") int student_id,
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id, List<Answer> answers) {

        System.out.println("Student Answering a questions");
        //todo parse json
        return studentSurveyAnswerService.postAnswersToQuestion(class_id,
                student_id, survey_id, answers);
    }
}
