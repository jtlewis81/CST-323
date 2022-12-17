package com.gcu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModel 
{
    private String Email;
    
    @NotNull(message="Username is a required field")
    @Size(min=2, max=32, message="Username must be between 2 and 32 characters")
    private String Username;

    @NotNull(message="Password is a required field")
    @Size(min=8, max=64, message="Password must be between 8 and 64 characters")
    private String Password;
    
    
    public UserModel(String username, String password, String email)
    {
		this.Username = username;
		this.Password = password;
		this.Email = email;
	}
    
    public UserModel(String username, String password)
    {
    	this.Username = username;
    	this.Password = password;
    }
    
    public UserModel() {
    }
    
    
    public String getUsername() { return Username; }
    public void setUsername(String username) { this.Username = username; }
    
    public String getPassword() { return Password; }
    public void setPassword(String password) { this.Password = password; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }
    
}