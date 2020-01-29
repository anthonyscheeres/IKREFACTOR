package main.java.nl.iipsen2server.models;

public class ValidateEmailModel {
	String email ;
	String tokenFromDatabase ;
	String username ;
	String yourDomain;
	String token;
	public ValidateEmailModel(String email, String tokenFromDatabase, String username, String yourDomain,
			String token) {
		super();
		this.email = email;
		this.tokenFromDatabase = tokenFromDatabase;
		this.username = username;
		this.yourDomain = yourDomain;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTokenFromDatabase() {
		return tokenFromDatabase;
	}
	public void setTokenFromDatabase(String tokenFromDatabase) {
		this.tokenFromDatabase = tokenFromDatabase;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getYourDomain() {
		return yourDomain;
	}
	public void setYourDomain(String yourDomain) {
		this.yourDomain = yourDomain;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
