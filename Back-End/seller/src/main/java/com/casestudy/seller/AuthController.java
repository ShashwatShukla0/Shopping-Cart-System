package com.casestudy.seller;

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

import com.casestudy.seller.models.AuthenticationRequest;
import com.casestudy.seller.models.AuthenticationResponse;
import com.casestudy.seller.models.UserModel;
import com.casestudy.seller.models.UserRepository;
import com.casestudy.seller.services.UserService;
import com.casestudy.seller.utils.JwtUtils;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
	
	
	@PostMapping("/nseller")
	private ResponseEntity<?> subscribeClient(@RequestBody  AuthenticationRequest authenticationRequest)
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
				return ResponseEntity.ok(new AuthenticationResponse("Seller Already exists with email " + username));
			}
		
		
		try 
		{
			userRepository.save(usermodel);
		} 
		catch(Exception e) 
		{
			return ResponseEntity.ok(new AuthenticationResponse("Error during making account " + username));
		}
		
		return ResponseEntity.ok(new AuthenticationResponse("Succesful Login " + username));
		
	}
	
	
	@PostMapping("/seller")
	private ResponseEntity<?> authenticateClient(@RequestBody  AuthenticationRequest authenticationRequest)
	{
		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();
		
		try 
		{
		    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}
		catch (Exception e) 
		{
			return ResponseEntity.ok(new AuthenticationResponse("Error during Seller Authentication " + email));
		}
		
		UserDetails loadedUser = userService.loadUserByUsername(email);
		
		String generatedToken = jwtUtils.generateToken(loadedUser);
		return ResponseEntity.ok(new AuthenticationResponse(generatedToken));
	}

}
