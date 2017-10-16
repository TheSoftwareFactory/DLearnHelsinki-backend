package org.dlearn.helsinki.skeleton.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.ClassWithAllGroups;
import org.dlearn.helsinki.skeleton.service.ClassService;
import org.dlearn.helsinki.skeleton.model.ClassThemeAverage;
import org.dlearn.helsinki.skeleton.model.ListClassThemeAverage;
import org.dlearn.helsinki.skeleton.service.ProgressionService;

public class TeacherClassResource {	
    // request teachers/{teacher_id}/classes
    // returns the teacher's classes based on the teacher_id.
    // TODO implement to answer to request with the classes of the teacher.
    private final ClassService classService = new ClassService();
    private final ProgressionService progression = new ProgressionService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClassWithAllGroups> getClasses(@PathParam("teacher_id") int teacher_id) {
    	System.out.println("producing list of classes with groups");
        return classService.getAllClassesWithGroups(teacher_id);
    }
    
    @GET
    @Path("/{class_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListClassThemeAverage>getClassAverage(
            @PathParam("class_id") int class_id,
            @PathParam("amount") int amount) {
        return progression.getClassProgression(class_id, amount);
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

    @Path("/{class_id}/students")
    public TeacherClassStudentResource getClassStudent() {
        return new TeacherClassStudentResource();
    }
}
