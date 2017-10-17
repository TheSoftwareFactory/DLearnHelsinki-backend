package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

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

    // GET student info /{student_id}/

    // GET student info /{student_id}/surveys

    // GET student info /{student_id}/surveys/{survey_id}/answers
    // TODO Create a proper subresource.
    @GET
    @Path("/{student_id}/surveys/{survey_id}/answers")
    public List<StudentThemeAverage> getStudentThemeAverage(
            @PathParam("class_id") int class_id,
            @PathParam("survey_id") int survey_id,
            @PathParam("student_id") int student_id) {
        return teacherClassStudent.getStudentThemeAverage(survey_id, student_id);
    }

    @POST
    @Path("/{student_id}/move_to_group/{group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> change_group(@PathParam("class_id") int class_id, @PathParam("student_id") int student_id, @PathParam("group_id") int group_id) {
        moveToGroup.moveStudentToGroup(class_id, student_id, group_id);
        return group.getAllGroupsTheStudentIsIn(student_id);
    }

    @GET
    @Path("/{student_id}")
    public Student getStudent(
            @PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id) {
        System.out.println("fetching student");
        return teacherStudentService.getStudent(student_id);
    }

    @GET
    @Path("/{student_id}/progression/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListStudentThemeAverage> getProgression(
            @PathParam("class_id") int class_id,
            @PathParam("student_id") int student_id,
            @PathParam("amount") int amount) {
        return progression.getStudentClassProgression(class_id, student_id, amount);
    }

    @GET
    public List<Student> getListOfStudents(@PathParam("class_id") int class_id) {
        System.out.println("fetching list of students from class");
        return teacherStudentService.getAllStudentsFromClass(class_id);
    }

}
