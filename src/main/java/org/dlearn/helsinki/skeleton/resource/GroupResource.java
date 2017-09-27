package org.dlearn.helsinki.skeleton.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.Group;
import org.dlearn.helsinki.skeleton.model.Student;

import jersey.repackaged.com.google.common.collect.Lists;

public class GroupResource {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> getGroups() {
    	System.out.println("test");
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group(3, "name", 0, 0));
        return groups;
    }
    
    @Path("/{group_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Group getGroupInfo(@PathParam("group_id") int group_id) {
        return new Group(0, null, 0, 0);
    }
    
    @Path("/{group_id}/survey")
    @GET
    public GroupSurveyResource getGroupSurvey(@PathParam("group_id") int group_id) {
        return new GroupSurveyResource();
    }

}
