package main.java.nl.iipsen2server.resources;


import main.java.nl.iipsen2server.controlllers.ExperimentController;
import main.java.nl.iipsen2server.dao.ExperimentDatabase;
import main.java.nl.iipsen2server.models.ExperimentModel2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author AnthonySchuijlenburg, AnthonyScheeres
 */
@Path("/experiment")
public class ExperimentResource {

    private ExperimentController experimentcontroller = new ExperimentController();

    /**
     * @author Jesse Poleij
     *
     */
    @POST
    @Path("/{token}/remove")
    public void deleteExperiment(@PathParam("token") String token, ExperimentModel2 experimentModel) {
        experimentcontroller.deleteExperimentFromDatabase(experimentModel, token);
    }
    /**
     * @author Jesse Poleij
     *
     */

    @GET
    @Path("/{token}/overview")
    @Produces(MediaType.TEXT_PLAIN)
    public void showOverview(@PathParam("token") String token){
        experimentcontroller.showOverviewFromDatabase(token);
    }
	/**
	 * @author AnthonySchuijlenburg
	 */
    @GET
    @Path("/show")
    @Produces(MediaType.TEXT_PLAIN)
    public String showExperiments(){
        ExperimentDatabase experimentDatabase = new ExperimentDatabase();
        return experimentDatabase.showExperimentsFromDatabase();
    }

    /**
     * @author AnthonySchuijlenburg
     */
    @GET
    @Path("/showSingle/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String showSingleExperiment(@PathParam("id") int id){
        ExperimentController experimentController = new ExperimentController();
        return experimentController.showSingleExperimentById(id);
    }

    
    
    
    
    /**
     * @author Anthony Scheeres
     */
    @GET
    @Path("/showAllHashmap")
    @Produces(MediaType.TEXT_PLAIN)
    public String showAllExperiments() throws Exception {
        ExperimentDatabase experimentDatabase = new ExperimentDatabase();
        return experimentDatabase.showAllExperimentHashmap();
    }
    
    
    
    /**
     * @author Anthony Scheeres
     */
    @GET
    @Path("/showAllJson")
    @Produces(MediaType.TEXT_PLAIN)
    public String showAllExperimentsJson() throws Exception {
        ExperimentDatabase experimentDatabase = new ExperimentDatabase();
        return experimentDatabase.showAllExperimentAsJsonString();
    }
}
