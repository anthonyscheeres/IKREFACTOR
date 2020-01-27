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
        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));
        String response;

        if (!authenticationController.hasSuperPermission(employeeId)) {
        	response = Response.fail.toString();
        	return response;
        }
        //write model to db
        ProjectDAO projectDatabase = new ProjectDAO();
        projectDatabase.sendProject(new ExperimentModel2());
        response = Response.success.toString();

        return response;
    }

    /*
    *@author Cyriel van der Raaf
    */
    public String deleteCreateProject(ExperimentModel2 project, String token){
        //validate user
        TokenController tokenController = new TokenController();
        AuthenticationController authenticationController = new AuthenticationController();
        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));

        if (!authenticationController.hasSuperPermission(employeeId)) {
            return Response.fail.toString();
        }

        //project delete model
        ProjectDAO deleteDatabase = new ProjectDAO();
        deleteDatabase.deleteProject(new ExperimentModel2());


        return Response.fail.toString();
    }
}
