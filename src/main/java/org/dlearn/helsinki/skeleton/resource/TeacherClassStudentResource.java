package org.dlearn.helsinki.skeleton.resource;

import java.util.List;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.ListStudentThemeAverage;

import org.dlearn.helsinki.skeleton.model.Student;
import org.dlearn.helsinki.skeleton.model.StudentThemeAverage;
import org.dlearn.helsinki.skeleton.service.GroupService;
import org.dlearn.helsinki.skeleton.service.MoveToGroupService;
import org.dlearn.helsinki.skeleton.service.ProgressionService;
import org.dlearn.helsinki.skeleton.service.TeacherClassStudentService;
import org.dlearn.helsinki.skeleton.service.TeacherStudentService;

public class TeacherClassStudentResource {
    private final TeacherStudentService teacherStudentService = new TeacherStudentService();
    private final TeacherClassStudentService teacherClassStudent = new TeacherClassStudentService();
    private final MoveToGroupService moveToGroup = new MoveToGroupService();
    private final ProgressionService progression = new ProgressionService();
    private final GroupService group = new GroupService();

    /**
     * GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}/surveys/{survey_id}/answers
     * @param class_id
     * @param survey_id
     * @param student_id
     * @return Theme averages for a student
     */
    @GET
    @Path("/{student_id}/surveys/{survey_id}/answers")
    public List<StudentThemeAverage> getStudentThemeAverage(
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("student_id") int student_id) {
        return teacherClassStudent.getStudentThemeAverage(survey_id,
                student_id);
    }

    /**
     * Move student to other group
     * POST teachers/{teacher_id}/classes/{class_id}/students/{student_id}/move_to_group/{group_id}
     * @param class_id
     * @param student_id
     * @param group_id
     * @return group
     */
    @POST
    @Path("/{student_id}/move_to_group/{group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Group change_group(@PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id,
            @PathParam("group_id") int group_id) {
        if (moveToGroup.moveStudentToGroup(class_id, student_id, group_id)) {
            return group.getTheGroupStudentIsIn(class_id, student_id)
                    .orElse(null);
        } else {
            return null;
        }
    }
    
    /**
     * remove student from a group/class
     * DELETE teachers/{teacher_id}/classes/{class_id}/students/{student_id}/remove_from_group/{group_id}
     * @param class_id
     * @param group_id
     * @param student_id 
     * @return  
     */
    @Path("/{student_id}/remove_from_group/{group_id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean removeStudentFromGroup(
            @PathParam("class_id") int class_id,
            @PathParam("group_id") int group_id,
            @PathParam("student_id") int student_id){
        
        return teacherStudentService.removeStudentFromGroup(class_id, group_id, student_id);
    
    }

    /**
     * GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}
     * @param class_id
     * @param student_id
     * @return student
     */
    @GET
    @Path("/{student_id}")
    public Student getStudent(@PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id) {
        System.out.println("fetching student");
        return teacherStudentService.getStudent(student_id);
    }

    /**
     * GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}/progression/{amount}
     * @param class_id
     * @param student_id
     * @param amount amount of items to get
     * @return student progression
     */
    @GET
    @Path("/{student_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListStudentThemeAverage> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id,
            @PathParam("amount") int amount) {
        return progression.getStudentClassProgression(class_id, student_id,
                amount);
    }

    /**
     * GET teachers/{teacher_id}/classes/{class_id}/students
     * @param class_id
     * @return All students from a class
     */
    @GET
    public List<Student> getListOfStudents(
            @PathParam("class_id") int class_id) {
        System.out.println("fetching list of students from class");
        return teacherStudentService.getAllStudentsFromClass(class_id);
    }

}
