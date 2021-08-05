package com.casestudy.seller.models;

public class AuthenticationResponse 
{
	private String response;

	public AuthenticationResponse() 
	{
	}

	public String getResponse() 
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}
    
	
	//constructor  using field.. so that this can be used in autocontroller authenticationresponse
	public AuthenticationResponse(String response) 
	{
		this.response = response;
	}

}
