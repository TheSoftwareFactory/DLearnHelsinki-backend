package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.service.TeacherClassStudentService;

public class TeacherClassStudentResource {
	
	TeacherClassStudentService service = new TeacherClassStudentService();
	
	// GET student info /{student_id}/
	
	// GET student info /{student_id}/surveys
	
	// GET student info /{student_id}/surveys/{survey_id}/answers
	@GET
	@Path("/{student_id}/surveys/{survey_id}/answers")
	public List<StudentThemeAverage> getStudentThemeAverage(@PathParam("class_id") int class_id,
			@PathParam("survey_id") int survey_id,
			@PathParam("student_id") int student_id){
		return service.getStudentThemeAverage(survey_id,student_id);
		
	}

}