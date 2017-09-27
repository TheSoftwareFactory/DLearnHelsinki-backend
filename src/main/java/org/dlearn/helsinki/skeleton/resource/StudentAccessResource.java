package org.dlearn.helsinki.skeleton.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/student")
public class StudentAccessResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkLogin() {
        return "logged in";
    }


    @GET
    @Path("/groups")
    public GroupResource getStudentFromId() {
    	System.out.println("groups");
        return new GroupResource();
    }
}
