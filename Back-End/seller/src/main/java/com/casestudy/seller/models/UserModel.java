package com.casestudy.seller.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sellers") 
public class UserModel 
{
	@Id
	private String id;
	private String email;
	private String username;
	private String password;
	
	public String getId() 
	{
		return id;
	}
	
	public UserModel() 
	{
		
	}
	
    
	public String getEmail() 
	{
		return email;
		
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	

}
