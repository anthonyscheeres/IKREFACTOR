package main.java.nl.iipsen2server.controlllers;

import java.util.HashMap;
import java.util.List;

import main.java.nl.iipsen2server.dao.DatabaseUtilities;
import main.java.nl.iipsen2server.dao.UserDAO;
import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.Response;

public class TokenController {
	 
		/**
		*
		* @author Anthony Scheeres
		 *  
		* get tokens from database
		*
		*/
		private HashMap<String, List<String>> getTokens() throws Exception {
		 UserDAO userDatabase = new UserDAO();
		 return userDatabase.getTokens();
	 }
	 
	 
	 private boolean isStringEmty(String token){
		  return token.length()!=0;	
	 }
	 
	 
	 
		/**
		*
		* @author Anthony Scheeres
		 *  
		* 
		*looks if token exist in hashmap
		*/
		private String findValideTokenInHashmap(HashMap<String, List<String>> hashmap, String token) {
		 ////hashmap.get("token").get(0));
		 String result = null ;
		 
		 
		   for (int index = 0; index <hashmap.get("token").size(); index++) {
			  String myToken =  hashmap.get("token").get(index);
			   if (isStringEmty(myToken)) {
				   if (myToken.equals(token)) {
					   result = hashmap.get("user_id").get(index);; 
				   }
			   }
			   }
		   return result;
	 }
	
	 
	 
	 /**
	 *
	 * @author Anthony Scheeres
	 *  
	 * 
	 *
	 */
	 public String tokenToUserIdFromDatabase(String token) {
		  HashMap < String, List < String >> hashmap = null;
		  try {
			  hashmap = getTokens();
		   return findValideTokenInHashmap( hashmap, token);
		   }
		   catch (Exception e) {
		   e.printStackTrace();
		  }
		  return Response.fail.toString();
	 }
	 
	 
}
