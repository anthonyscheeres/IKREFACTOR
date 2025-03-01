package main.java.nl.iipsen2server.models;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserModel {
	@NotNull
	private String username;
	@NotNull
	private String password;
	@Pattern(regexp="^[0-9]*$")
	private long id;

	private List<Enum> rechten;
	private String email;
	
public UserModel() {
		
	}

public UserModel(@NotNull String username, @NotNull String password, @Pattern(regexp = "^[0-9]*$") long id,
		List<Enum> rechten, String email) {
	super();
	this.username = username;
	this.password = password;
	this.id = id;
	this.rechten = rechten;
	this.email = email;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public List<Enum> getRechten() {
	return rechten;
}

public void setRechten(List<Enum> rechten) {
	this.rechten = rechten;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
	
}
