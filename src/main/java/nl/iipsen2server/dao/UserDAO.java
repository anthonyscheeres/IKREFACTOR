package main.java.nl.iipsen2server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.nl.iipsen2server.controlllers.AuthenticationController;
import main.java.nl.iipsen2server.controlllers.MailController;
import main.java.nl.iipsen2server.controlllers.UserController;
import main.java.nl.iipsen2server.models.*;


public class UserDAO {

	private String tableName = "app_user";
	private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);


	/**
	 * @author Anthony Scheeres
	 * <p>
	 * get tokens from database
	 */
	public HashMap<String, List<String>> getTokens() throws Exception {
		DatabaseUtilities databaseUtilities = new DatabaseUtilities();
		String query = String.format("select user_id, token from %s;", tableName);
		return databaseUtilities.connectThisDatabase(databaseModel, query);
	}
	/**
	 * @author Anthony Scheeres
	 */
	public boolean hasEnumHandeler(long employeeId, String permission) {
		String query2 = "select permission from app_user where user_id=?;";
		UserDAO userDatabase = new UserDAO();
		return userDatabase.hasPermissionOnApi(permission, Long.toString(employeeId), query2);
	}
    /**
     * @author Anthony Scheeres
     */

    public boolean hasPermissionOnApi(String permission, String u, String query2) {
        PreparedStatmentDatabaseUtilities dUtilities = new PreparedStatmentDatabaseUtilities();
        HashMap<String, List<String>> hashMap;
        List<String> array = new ArrayList<String>();
        array.add(u);
        try {
            hashMap = dUtilities.connectDatabaseHashmap(databaseModel, query2, array);
            boolean hasPermission = hashMap.get("permission").contains(null);
            if (hasPermission) {
                return false;
            }
            if (!hashMap.get("permission").get(0).contains(permission)) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }


    /**
     * @author Anthony Scheeres
     */
    public void changeToken(String token, int id) {
        String query = String.format(""
                + "UPDATE %s "
                + "SET token = '%s' "
                + "WHERE user_id = %d;", tableName, token, id);
        //query);
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        try {
            databaseUtilities.connectThisDatabaseThrowQueryToDatabaseFromConfigFile(databaseModel, query);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * @author Anthony Scheeres
     */
    public String showUserJson() throws Exception {
        String query = String.format("select username,permission from %s;", tableName);
        //query);
        DatabaseUtilities databaseUtiles = new DatabaseUtilities();
        return databaseUtiles.connectThisDatabaseThrowQueryToDatabaseFromConfigFile(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public String showOneUserPermission(AccountModel u) {
        PreparedStatmentDatabaseUtilities databaseUtil = new PreparedStatmentDatabaseUtilities();
        String resultToReturn = null;
        String queryForSelectingPermissions =
                "select username, permission FROM app_user\r\n" +
                        "WHERE username = ? ;";
        List<String> array = new ArrayList<String>();
        array.add(u.getUsername());
        try {
            resultToReturn = databaseUtil.connectDatabaseThrowQueryReturnsJson(databaseModel, queryForSelectingPermissions, array, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultToReturn;
    }


    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUserInfo() throws Exception {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format("select username, password, user_id, token, permission from %s order by user_id;", tableName);
        return d.connectThisDatabase(databaseModel, query);

    }


    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUsers() throws Exception {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format("select username, user_id from %s;", tableName);
        return d.connectThisDatabase(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public String insertHandlerUser(HashMap<String, List<String>> e1, UserModel u) throws Exception {
        PreparedStatmentDatabaseUtilities pUtilites = new PreparedStatmentDatabaseUtilities();
        MailController m = new MailController();
        UserController r = new UserController();
        long id = r.createUserId2(e1.get("user_id"));
        String query2 = "INSERT INTO app_user(username, password, user_id, email, token) VALUES (" +
                "?," +
                "?," +
                "?," +
                "?," +
                "?" +
                ");";
        String token = m.generateToken();
        List<String> variables = new ArrayList<>();
        variables.add(u.getUsername());
        variables.add(u.getPassword());
        variables.add(String.format("%d", id));
        variables.add(u.getEmail());
        variables.add(token);
        pUtilites.connectDatabaseThrowQueryReturnsJson(databaseModel, query2, variables, false);
        return token;
    }

    /**
     * @author Anthony Scheeres
     */
    public void removeUserModel2(AccountModel u) {
        PreparedStatmentDatabaseUtilities f = new PreparedStatmentDatabaseUtilities();
        String query =
                "DELETE FROM app_user\r\n" +
                        "WHERE username = ? ;";
        List<String> f1 = new ArrayList<String>();
        f1.add(u.getUsername());
        try {
            f.connectDatabaseThrowQueryReturnsJson(databaseModel, query, f1, false);
        } catch (Exception e) {

        }
    }



/**
 *
 * @author Anthony Scheeres
 * 
 *
 */
public void removeUserMode(AccountModel u) {
	PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
	String deletequery =
			"DELETE FROM app_user\r\n" +
					"WHERE username = ?;";
	String logquery =
			"DELETE FROM logs\r\n" +
					"WHERE id = ?;";
	List<String> f1 = new ArrayList<String>();
	f1.add(u.getUsername());
	List<String> f2 = new ArrayList<String>();
	f2.add(u.getId());
	try {
		preparedStatmentDatabaseUtilities.connectDatabaseThrowQueryReturnsJson(databaseModel, deletequery, f1, false);
	} catch (Exception e) {
	}
}
}