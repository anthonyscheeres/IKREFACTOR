package main.java.nl.iipsen2server.resources;

import main.java.nl.iipsen2server.controlllers.ProjectsController;
import main.java.nl.iipsen2server.models.ProjectModel2;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/project")
public class ProjectResource {

    /*
    *@author Cyriel van der Raaf
    *@POST
    *@Path
    *@Consumes
    *@return feedback
    */
    @POST
    @Path("/{token}/createProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String openProject(ProjectModel2 project, @PathParam("token") String token){
        ProjectsController projectsController = new ProjectsController();
        return projectsController.handleCreateProject(project, token);
    }

    /*
    *@author Cyriel van der Raaf
    *@POST
    *@Path
    *@Consumes
    *@return feedback
    */
    @POST
    @Path("/{token}/createProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteProject(ProjectModel2 project, @PathParam("token") String token){
        ProjectsController projectsController = new ProjectsController();
        return projectsController.deleteCreateProject(project, token);
    }

}
