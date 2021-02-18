package com.prc391.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prc391.models.User;
import com.prc391.models.UserDetails;
import com.prc391.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userDTO = this.repo.findByUsername(username);

		if (userDTO == null) {
			throw new UsernameNotFoundException("User " + username + " was not found!");
		}

		return new UserDetails(userDTO);
	}

	public UserDetails loadById(int id) throws UsernameNotFoundException {
		User userDTO = this.repo.findById(id).get();

		if (userDTO == null) {
			throw new UsernameNotFoundException("User " + id + " was not found!");
		}

		return new UserDetails(userDTO);
	}
	
}
