package main.java.nl.iipsen2server.dao;

import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.ExperimentModel2;

import java.util.ArrayList;
import java.util.List;

public class ExperimentDAO {
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();

/**
 * @author Jesse Poleij
 *
 */
    public void deleteExperiment(ExperimentModel2 project) {
        String query = "DELETE FROM projects WHERE id = ?;";

        List< String > arrayList = new ArrayList<>();
        arrayList.add(Long.toString(project.getId()));

        try {
            preparedStatmentDatabaseUtilities.connectDatabaseThrowQueryReturnsJson(databaseModel, query, arrayList, false);
        } catch (Exception e) {

        }

    }
    public void showOverview(ExperimentModel2 project) {
        String query = "";



    }
}
