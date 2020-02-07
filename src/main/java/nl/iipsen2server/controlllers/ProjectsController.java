package main.java.nl.iipsen2server.controlllers;

import main.java.nl.iipsen2server.dao.ProjectDAO;
import main.java.nl.iipsen2server.models.ExperimentModel2;
import main.java.nl.iipsen2server.models.Response;
import main.java.nl.iipsen2server.models.Status;

public class ProjectsController {
    /*
    * @author Cyriel van der Raaf
    */
    public String handleCreateProject(ExperimentModel2 project, String token){
        //validate user
        TokenController tokenController = new TokenController();
        AuthenticationController authenticationController = new AuthenticationController();
        long employeeId = Long.parseLong(tokenController.tokenToUserIdFromDatabase(token));
        String responseFromController;

        if (!authenticationController.hasSuperPermission(employeeId)) {
        	responseFromController = Response.fail.toString();
        	return responseFromController;
        }
        //write model to db
        ProjectDAO projectDatabase = new ProjectDAO();
        projectDatabase.sendProject(new ExperimentModel2());
        responseFromController = Response.success.toString();

        return responseFromController;
    }

    /*
    *@author Cyriel van der Raaf
    */
    public String updateProjectByRecreatingIt(ExperimentModel2 project, String token){
        //validate user
        TokenController tokenController = new TokenController();
        AuthenticationController authenticationController = new AuthenticationController();
        long employeeId = Long.parseLong(tokenController.tokenToUserIdFromDatabase(token));

        if (!authenticationController.hasSuperPermission(employeeId)) {
            return Response.fail.toString();
        }

        //project delete model
        ProjectDAO deleteDatabase = new ProjectDAO();
        deleteDatabase.deleteProjectFromDatabase(new ExperimentModel2());


        return Response.fail.toString();
    }
}
