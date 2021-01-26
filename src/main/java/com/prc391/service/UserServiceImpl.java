package com.prc391.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prc391.models.User;
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
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority(userDTO.getRole());
        grantList.add(authority);
        
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails
				.User(userDTO.getUsername(), userDTO.getPassword(), grantList);
		return userDetails;
	}

}
