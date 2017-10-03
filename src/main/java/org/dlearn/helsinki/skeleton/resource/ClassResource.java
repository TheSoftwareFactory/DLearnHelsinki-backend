package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Classes;
import org.dlearn.helsinki.skeleton.model.Survey;

import jersey.repackaged.com.google.common.collect.Lists;

public class ClassResource {
	
	
	// request teachers/{teacher_id}/surveys/
	// returns all the surveys from teacher based on the teacher_id. a sort of history
	// TODO implement to answer to history request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Classes> getClasses(@PathParam("teacher_id") int teacher_id) {
        //return surveyService.getSurveysFromTeacherId(teacher_id);
        return Lists.newArrayList(
                new Classes(),
                new Classes()
            );
    }
    
    @Path("/{class_id}/surveys")
    public SurveyResource getSurveyResource(@PathParam("class_id") int class_id) {
    	System.out.println("calling classes");
    	return new SurveyResource();
    }

}
