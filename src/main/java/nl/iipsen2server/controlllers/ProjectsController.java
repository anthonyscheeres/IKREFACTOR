package main.java.nl.iipsen2server.controlllers;

import main.java.nl.iipsen2server.dao.ProjectDatabase;
import main.java.nl.iipsen2server.models.ProjectModel2;
import main.java.nl.iipsen2server.models.Status;

public class ProjectsController {
    /*
    * @author Cyriel van der Raaf
    */
    public String handleCreateProject(ProjectModel2 project, String token){
        //validate user
        TokenController tokenController = new TokenController();
        AuthenticationController authenticationController = new AuthenticationController();
        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));

        if (!authenticationController.hasSuperPermission(employeeId)) {
            return "fail";
        }
        //write model to db
        ProjectDatabase projectDatabase = new ProjectDatabase();
        projectDatabase.sendProject(new ProjectModel2());


        return "fail";
    }

    /*
    *@author Cyriel van der Raaf
    */
    public String deleteCreateProject(ProjectModel2 project, String token){
        //validate user
        TokenController tokenController = new TokenController();
        AuthenticationController authenticationController = new AuthenticationController();
        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));

        if (!authenticationController.hasSuperPermission(employeeId)) {
            return "fail";
        }

        //project delete model
        ProjectDatabase deleteDatabase = new ProjectDatabase();
        deleteDatabase.deleteProject(new ProjectModel2());


        return "fail";
    }
}
