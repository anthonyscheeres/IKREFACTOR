package main.java.nl.iipsen2server.controlllers;

import java.util.HashMap;
import java.util.List;

import main.java.nl.iipsen2server.dao.DatabaseUtilities;
import main.java.nl.iipsen2server.dao.UserDatabase;
import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;

public class TokenController {
	 
		/**
		*
		* @author Anthony Scheeres
		 *  
		* get tokens from database
		*
		*/
		private HashMap<String, List<String>> getTokens() throws Exception {
		 UserDatabase userDatabase = new UserDatabase();
		 return userDatabase.getTokens();
	 }
	 
	 
	 
	 
	 
	 
		/**
		*
		* @author Anthony Scheeres
		 *  
		* 
		*looks if token exist in list
		*/
		private String findValideToken(HashMap<String, List<String>> hashmap, String token) {
		 System.out.println(hashmap.get("token").get(0));
		   for (int index = 0; index <hashmap.get("token").size(); index++) {
			   if (hashmap.get("token").get(index).length()!=0) {
		    if (hashmap.get("token").get(index).equals(token)) {
		     return hashmap.get("user_id").get(index);
		    }
			   }
		   }return null;
	 }
	
	 
	 
	 /**
	 *
	 * @author Anthony Scheeres
	 *  
	 * 
	 *
	 */
	 public String tokenToUserId(String token) {
		  HashMap < String, List < String >> hashmap = null;
		  try {
			  hashmap = getTokens();
		   return findValideToken( hashmap, token);
		   }
		   catch (Exception e) {
		   e.printStackTrace();
		  }
		  return "fail";
	 }
	 
	 
}
