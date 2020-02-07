package main.java.nl.iipsen2server.controlllers;

import main.java.nl.iipsen2server.dao.ExperimentDatabase;

import main.java.nl.iipsen2server.dao.ExperimentDAO;
import main.java.nl.iipsen2server.models.ExperimentModel2;
import main.java.nl.iipsen2server.models.Permission;



public class ExperimentController {

    private AuthenticationController authenticationController = new AuthenticationController();
    private TokenController tokenController = new TokenController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();
/**
 * @author Jesse Poleij
 *
 */
    public void deleteExperimentFromDatabase(ExperimentModel2 project, String token) {
        String userID = tokenController.tokenToUserIdFromDatabase(token);
        if(authenticationController.hasPermission(Long.parseLong(userID), Permission.DELETE.toString())){
            experimentDAO.deleteExperiment(project);
        }
    }

    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments(){
        ExperimentDatabase experimentDatabase = new ExperimentDatabase();
        return experimentDatabase.showExperimentsFromDatabase();
    }

    /**
     * @author AnthonySchuijleburg
     */
    public String showSingleExperimentById(int id){
        ExperimentDatabase experimentDatabase = new ExperimentDatabase();
        return experimentDatabase.showExperiment(id);
    }
  /* @author Jesse Poleij
  *
  */
    public void showOverviewFromDatabase(String token) {
        String userID = tokenController.tokenToUserIdFromDatabase(token);
        authenticationController.hasPermission(Long.parseLong(userID), Permission.READ.toString());
    }

}
