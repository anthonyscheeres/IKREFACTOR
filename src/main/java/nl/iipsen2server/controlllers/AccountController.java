package main.java.nl.iipsen2server.controlllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.LogModel;
import main.java.nl.iipsen2server.models.Permission;
import main.java.nl.iipsen2server.models.Response;
import main.java.nl.iipsen2server.models.RestApiModel;
import main.java.nl.iipsen2server.models.User;
import main.java.nl.iipsen2server.models.UserModel;
import main.java.nl.iipsen2server.models.ValidateEmailModel;
import main.java.nl.iipsen2server.dao.DatabaseUtilities;
import main.java.nl.iipsen2server.dao.PermissionDAO;
import main.java.nl.iipsen2server.dao.PreparedStatmentDatabaseUtilities;
import main.java.nl.iipsen2server.dao.UserDAO;
import main.java.nl.iipsen2server.models.AccountModel;


/**
 * @author Anthony Scheeres
 */


public class AccountController {
private UserDAO userDatabase = new UserDAO();
private PermissionDAO permissionDatabase = new PermissionDAO();
String domain = "OM.NL";

/**
*
* @author Anthony Scheeres

*
*
*/

/**
 * @return
 * @author Anthony Scheeres
 */
    public boolean giveRead2(String username) {
        return permissionDatabase.giveRead2(username);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean giveWrite2(String user) {
        return permissionDatabase.giveWrite2(user);
    }

    /**
     * @author Anthony Scheeres
     */
    public boolean giveDelete2(String user) {
        return permissionDatabase.giveDelete2(user);
    }

    /**
     * @author Anthony Scheeres
     */
    private String createUserModel(UserModel userModel) throws Exception {
        UserController r = new UserController();
        HashMap<String, List<String>> hashmap;
        String result = null;
        hashmap = userDatabase.getUsers();
        List<String> usernames = hashmap.get("username");
        
        if (r.checkIfUsernameExist(usernames, userModel.getUsername()) != true) {
        	  result =  userDatabase.insertHandlerUser(hashmap, userModel);
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean checkInputValide(String email, String password) {
        MailController m = new MailController();
        if (!m.isValidEmailAddress(email)) {
            return false;
        }

        if (password.length() == 0) {
            return false;
        }
        return true;
    }


    /**
     * @author Anthony Scheeres
     */
    public String handleCreateUserModel2(UserModel u) {
    	String fail = Response.fail.toString();
        if (!checkInputValide(u.getEmail(), u.getPassword())) {
            return fail;
        }
        try {
            String token = createUserModel(u);
            if (!token.equals(null)) {
                validateEmail(token, u.getEmail());
                return token;
            }
            return fail;
        } catch (Exception e2) {

        }
        return fail;
    }


    /**
     * @author Anthony Scheeres
     */
    private void validateEmail(String token, String email) throws Exception {
    	String linkToServer = "http://%s:%s/user/%s/token";
    	String message = "Open de volgende link om uw email te valideren: ";
    	String link = message + linkToServer;
    	RestApiModel database =   DataModel.getApplicationModel().getServers().get(0).getRestApi().get(0);
    	String title = "Valideer u email!";
        MailController.sendMail(String.format(
               link,
                database.getHostName(),
                database.getPortNumber(),
                token
                ),
                "testlab",
                email,
                title);
    }

 /**
  *
  * @author Anthony Scheeres
 * @throws Exception 
  *  
  *
  */
 public String checkLogin(UserModel u) throws Exception {
  HashMap < String, List < String >> hashmap;
  String response = Response.fail.toString();
   hashmap = userDatabase.getUserInfo();
   List<String> users = hashmap.get(User.username.toString());
   String usernameFromClient = u.getUsername();
   String passwordFromClient = u.getPassword();
   for (int index = 0; index < users.size(); index++) {
	   
	   String username = hashmap.get(User.username.toString()).get(index);
	   
	   String passwordFromDatabase = hashmap.get(User.password.toString()).get(index); 
	   
	   String token = hashmap.get(User.token.toString()).get(index);
	   String permission = hashmap.get(User.permission.toString()).get(index);
	   String UserId = hashmap.get(User.user_id.toString()).get(index);
	   String responseToUser = GetLoginInformation(username, usernameFromClient, passwordFromDatabase,  passwordFromClient, permission, UserId, token);
	   
	   if (!responseToUser.equals(response)) {
		   return responseToUser;
	   }
	  
   }

  return response;
 }
 
 
 /**
  * @author Anthony Scheeres
  */
 private String GetLoginInformation(String username, String username2, String passwordFromDatabase,  String passwordFromClient, String permission, String UserId, String token){
	 String failtResponse = Response.fail.toString();
	
	  if (checkCredentials(username, username2, passwordFromDatabase,  passwordFromClient)) {
	    	boolean hasPermission = permission.length() ==0;
	    	if(hasPermission) {
	    
	    		return token;
	    	}
	    	if (permission.contains(Permission.READ.toString())) {
	    		 String newToken =  askNewTokenForAccount(Integer.parseInt(UserId));
	    		  return newToken;
	    	}
	     return token;
	    }
	  return failtResponse;
 }
 
 /**
  * @author Anthony Scheeres
  */
private String askNewTokenForAccount(int id) {
	  MailController mailController = new MailController();
	  UserDAO userDatabase = new UserDAO();
	  String newToken = mailController.generateToken();
	  userDatabase.changeToken(newToken, id);
	  return newToken;
}
 
 
 

 /**
  *
  * @author Anthony Scheeres
  *  
  * 
  *
  */
 public boolean checkCredentials(String username,String username2, String password, String password2){
  return username.equals(username2) && password.equals(password2);
 }

 public String handleValidateToken(String token) {
     String response = Response.fail.toString();
	 try {
		return validateToken(token);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return response;
 }
 /**
  * @author Anthony Scheeres
  * @throws Exception 
  */
 public String validateToken(String token) throws Exception  {
     MailController mailController = new MailController();
     HashMap<String, List<String>> data = mailController.getTokens();
     String response = Response.fail.toString();
    
     
     //loop over database records
     for (int i = 0; i < data.get(User.token.toString()).size(); i++) {
     	String email = data.get(User.email.toString()).get(i); //get mail from hashmap
     	String tokenFromDatabase = data.get(User.token.toString()).get(i); //get token  from hashmap
     	String username = data.get(User.username.toString()).get(i); //use username to uniquely identify a user 
     	String yourDomain = getDomeinNameFromMail(email.toUpperCase()); //get domain from email
     	
     	ValidateEmailModel validateEmailModel = new ValidateEmailModel(email, tokenFromDatabase, username, yourDomain, token);
     	
      response = isGivePermissionIfTokenValid(validateEmailModel);
 }
     return response ;
     
     
 }
 
 
 public String isGivePermissionIfTokenValid(	ValidateEmailModel validateEmailModel) throws Exception {
 	
 	String email = validateEmailModel.getEmail(); 
 	String tokenFromDatabase = validateEmailModel.getTokenFromDatabase();
 	String username = validateEmailModel.getUsername(); 
 	String yourDomain = validateEmailModel.getYourDomain(); 
 	String token = validateEmailModel.getToken();
 	 String response = Response.fail.toString();

 	 boolean isNullInput = email != null && tokenFromDatabase != null; 
 	 
 	if (isNullInput) {
 		
         if (token.equals(tokenFromDatabase)) {
         	
             if ( yourDomain.equals(domain)) {
             
             	//give read permissions
             	giveRead2(username);
             
                 
                 
                 
                 response = Response.success.toString();
             } 
             
             else response ="domein invalid, should be: " + domain.toLowerCase();
         
         }

         
     }   return response;
 }

 
    /**
     * @author Anthony Scheeres
     */
    private String getDomeinNameFromMail(String email){
    	return email.split("@")[1];
    }
    

    /**
     * @author Jesse Poleij, Anthony Scheeres
     */
    public void handleRemoveUser(AccountModel u, String token) {
        userDatabase.removeUserMode(u);
    }
}