package org.dlearn.helsinki.skeleton.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dlearn.helsinki.skeleton.model.StudentLof;
import org.dlearn.helsinki.skeleton.service.OutlierService;

@Path("/outliers")
public class OutlierResource {

    OutlierService outlierService = new OutlierService();

    // Takes a class_id
    // Returns all outliers of the class_id, with lof score over 1.0
    @GET
    @Path("/{class_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentLof> getClassOutliers(
            @PathParam("class_id") int class_id) {
        return outlierService.getClassOutliers(class_id);
    }

}
