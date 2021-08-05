package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.AuthenticationRequest;
import com.example.models.AuthenticationResponse;
import com.example.models.UserModel;
import com.example.models.UserRepository;
import com.example.services.UserService;
import com.example.utils.JwtUtils;

@RestController

 public class AuthController 
 {
	
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping("/dashboard")
	private String testingToken()
	{
		return "Welcome to the DASHBOARD" + SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	@PostMapping("/subs")
	@CrossOrigin(origins = "http://localhost:4200")
	private ResponseEntity<?> subscribeClient(@RequestBody  AuthenticationRequest authenticationRequest) throws Exception
	{
		
	
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		String email= authenticationRequest.getEmail();
		UserModel usermodel = new UserModel();
		usermodel.setUsername(username);
		usermodel.setPassword(password);
		usermodel.setEmail(email);
		
		
		
		UserModel userobj=userService.fetchUserByEmail(email);
		
			if(userobj!=null)
			{
				return ResponseEntity.ok(new AuthenticationResponse("User Already exists with email " + username));
			}
		
		
		try 
		{
			userRepository.save(usermodel);
		} 
		catch(Exception e) 
		{
			throw new Exception ("During Registration");
		}
		
		return ResponseEntity.ok(new AuthenticationResponse("Succesful Login " + username));
		
	}
	
	
	@PostMapping("/auth")
	@CrossOrigin(origins = "http://localhost:4200")
	private ResponseEntity<?> authenticateClient(@RequestBody  AuthenticationRequest authenticationRequest) throws Exception
	{
		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();
		
		try 
		{
		    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}
		catch (Exception e) 
		{
			 throw new Exception ("During Authorization");
		}
		
		UserDetails loadedUser = userService.loadUserByUsername(email);
		
		String generatedToken = jwtUtils.generateToken(loadedUser);
		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
	}
	 
	
 }