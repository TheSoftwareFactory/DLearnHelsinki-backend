package org.dlearn.helsinki.skeleton.resource;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.service.ClassService;
import org.dlearn.helsinki.skeleton.service.StudentService;

import jersey.repackaged.com.google.common.collect.Lists;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.service.ProgressionService;
import org.dlearn.helsinki.skeleton.service.SecurityService;

// Called by StudentAccess Students/1/classes
public class StudentClassResource {
    private final ClassService classService = new ClassService();
    private final SecurityService security = new SecurityService();
    private final ProgressionService progression = new ProgressionService();
    // request teachers/{teacher_id}/classes
    // returns the teacher's classes based on the teacher_id.
    // TODO implement to answer to request with the classes of the teacher.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Classes> getAllClassesStundentIsIn(@PathParam("student_id") int student_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return classService.getAllClassesStundentIsIn(student_id);
    }

    @Path("/{class_id}/surveys")
    public StudentSurveyResource getStudentSurveyResource(
            @PathParam("class_id") int class_id) {
        //System.out.println("calling classes");
        return new StudentSurveyResource();
    }
    
    @Path("/{class_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<List<StudentThemeAverage>> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("amount") int amount) {
        return security.getStudent()
                .map(s -> progression.getStudentClassProgression(class_id, s._id, amount))
                .orElse(Collections.EMPTY_LIST);
    }
}
