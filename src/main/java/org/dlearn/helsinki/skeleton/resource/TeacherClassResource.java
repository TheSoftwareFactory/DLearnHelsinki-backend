package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.service.StudentService;

import jersey.repackaged.com.google.common.collect.Lists;

public class TeacherClassResource {

    // request teachers/{teacher_id}/classes
    // returns the teacher's classes based on the teacher_id.
    // TODO implement to answer to request with the classes of the teacher.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Classes> getClasses(@PathParam("teacher_id") int teacher_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return Lists.newArrayList(new Classes(), new Classes());
    }

    @Path("/{class_id}/surveys")
    public TeacherClassSurveyResource getSurveyResource(
            @PathParam("class_id") int class_id) {
        System.out.println("calling class surveys");
        return new TeacherClassSurveyResource();
    }

    @Path("/{class_id}/groups")
    public TeacherGroupResource getGroupResource(
            @PathParam("class_id") int class_id) {
        System.out.println("calling class groups");
        return new TeacherGroupResource();
    }

    //@Path("/{class_id}/students")
    /*
    public TeacherStudentResource getgetAllStudentsFromClass(@PathParam("class_id") int class_id) {
    	System.out.println("calling classes");
    	return new TeacherStudentResource();
    }
    */

    @Path("/{class_id}/students")
    public TeacherClassStudentResource getClassStudent() {
        return new TeacherClassStudentResource();
    }
}
