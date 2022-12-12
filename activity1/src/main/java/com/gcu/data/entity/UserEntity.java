package com.gcu.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public class UserEntity 
{
	@Id
	private int id;
    @Column("Username")
	private String username;
    @Column("Password")
	private String password;
    @Column("Email")
	private String email;
    
    
    public UserEntity(int id, String username, String password, String email)
    {
    	this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
    
    public UserEntity(String username, String password, String email)
    {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    }
    
    public UserEntity(){}
    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}