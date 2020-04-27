package com.rajat.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajat.jwt.model.AuthenticationRequest;
import com.rajat.jwt.model.AuthenticationResponse;
import com.rajat.jwt.service.MyUserDetailsService;
import com.rajat.jwt.util.JwtUtil;

@RestController
public class LoginController {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/hello")
	public String hello() {
		return "hello world";
	}
	
	@PostMapping
	@RequestMapping(value = "/authenticate" )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request){
		try {
		authenticationManager.
		authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}catch(BadCredentialsException badCredentialsException) {
		 System.out.println("error="+badCredentialsException.getMessage());
			
		}
		
		final UserDetails userDetails=
				userDetailService.loadUserByUsername(request.getUsername());
		
		final String jwt=jwtUtil.generateToken(userDetails);		
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt)); 
		
	}
	
}
