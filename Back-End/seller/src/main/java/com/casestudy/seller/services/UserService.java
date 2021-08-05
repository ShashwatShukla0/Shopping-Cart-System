package com.casestudy.seller.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casestudy.seller.models.UserModel;
import com.casestudy.seller.models.UserRepository;



@Service
public class UserService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		UserModel foundedUser = userRepository.findByEmail(email);
		if(foundedUser == null) return null;
		String emailId = foundedUser.getEmail();
		String pwd = foundedUser.getPassword();
		return new User(emailId , pwd, new ArrayList<>());
	}
	public UserModel fetchUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}

}
