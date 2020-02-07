package main.java.nl.iipsen2server.dao;

import java.util.ArrayList;
import java.util.List;

import main.java.nl.iipsen2server.controlllers.AuthenticationController;
import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.Permission;
import main.java.nl.iipsen2server.models.AccountModel;

public class PermissionDAO {

	 String tableName = "app_user";
	 private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
	 AuthenticationController autenticationController = new AuthenticationController();
	 private UserDAO userDatabase = new UserDAO();




	 /**
	  *
	  * @author Anthony Scheeres
	 * @return 
	  * 
	  *
	  */
	 public boolean giveReadToAccountWithFollowingUsername(String username) {
		  Enum permissionReadToGive = Permission.READ;

		  return handleAutorizeGivePermission(username, permissionReadToGive);
	 }

	 
	 public boolean handleAutorizeGivePermission(String username, Enum permission) {
		  String queryUserHistPermissionOnApi = "select permission from app_user where username=?;";
		boolean hasAlreadyThisPermission = !userDatabase.hasPermissionOnApi(permission.toString(), username, queryUserHistPermissionOnApi);
		
		
		
	 if (hasAlreadyThisPermission) {
		  try {
			  givePermissionOnApi(username, permission);
		} catch (Exception e) {
		e.printStackTrace();
		}
		  
	 }return hasAlreadyThisPermission;
	 }
	 /**
	 *
	 * @author Anthony Scheeres
	 *  
	 * 
	 *
	 */
	 public boolean giveWrite2(String u) {
		  Enum permissionReadToGive = Permission.WRITE;
		  return handleAutorizeGivePermission(u, permissionReadToGive);
	 }

	 


	 /**
	  * @author Anthony Scheeres
	  */
	 public boolean giveDeleteToAccountWithFollowingUsername(String username) {
		  Enum permissionReadToGive = Permission.DELETE;

		  return handleAutorizeGivePermission(username, permissionReadToGive);
	 }







	 /**
	  *
	  * @author Anthony Scheeres
	  *
	  */
	 private void givePermissionOnApi(String u, Enum e) throws Exception {
	
		  PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
		  List < String > list = new ArrayList < String > ();
		  String queryupdatePermissionAddPermission = String.format("UPDATE app_user SET permission = array_append(permission,'%s') WHERE username = ?;", e);
		  list.add(u);
		  databaseController.connectDatabaseThrowQueryReturnsJson(databaseModel, queryupdatePermissionAddPermission, list, false);
	 }
	 
	 

	 /**
	  *
	  * @author Anthony Scheeres
	  *  
	  */
public boolean hasEnumHandeler(long employeeId, String permission) {
	  String queryGetPermissionById = "select permission from app_user where user_id=?;";
	return userDatabase.hasPermissionOnApi( permission, Long.toString(employeeId), queryGetPermissionById) ;	
}
}
