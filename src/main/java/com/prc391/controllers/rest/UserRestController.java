package com.prc391.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prc391.exception.ResourceNotFoundException;
import com.prc391.models.User;
import com.prc391.models.UserDetails;
import com.prc391.payload.request.LoginRequest;
import com.prc391.payload.response.JwtResponse;
import com.prc391.repositories.UserRepository;
import com.prc391.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
	
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		String jwt = tokenProvider.generateToken(userPrincipal);

		return ResponseEntity.ok(
				new JwtResponse(jwt,  
						userPrincipal.getUser().getUsername(), 
						userPrincipal.getUser().getAvatarLink(), 
						userPrincipal.getUser().getFullname(), 
						userPrincipal.getUser().isStatus(), 
						userPrincipal.getUser().getRole()));
	}

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Gets users by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
		return ResponseEntity.ok().body(user);
	}

}
