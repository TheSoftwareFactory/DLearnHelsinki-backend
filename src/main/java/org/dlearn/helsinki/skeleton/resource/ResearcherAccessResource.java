package org.dlearn.helsinki.skeleton.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.dlearn.helsinki.skeleton.exceptions.PasswordException;
import org.dlearn.helsinki.skeleton.model.NewTeacher;
import org.dlearn.helsinki.skeleton.model.Researcher;
import org.dlearn.helsinki.skeleton.model.Survey;
import org.dlearn.helsinki.skeleton.model.Teacher;
import org.dlearn.helsinki.skeleton.service.CreateNewUserService;
import org.dlearn.helsinki.skeleton.service.SecurityService;
import org.dlearn.helsinki.skeleton.service.SurveyService;

@Path("/researcher")
public class ResearcherAccessResource {
    private final SurveyService survey = new SurveyService();
    private final SecurityService security = new SecurityService();
    private final CreateNewUserService createNewUserService = new CreateNewUserService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Researcher getResearcher() {
        return security.getResearcher().orElse(null);
    }

    @Path("/surveys")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Survey> getSurveys() {
        return survey.getAllSurveys();
    }

    @POST
    @Path("/create_teacher")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Teacher createNewTeacher(NewTeacher teacher)
            throws PasswordException {
        try {
            return createNewUserService.createNewTeacher(teacher);
        } catch (PasswordException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Invalid password.").build());
        }
    }
}
