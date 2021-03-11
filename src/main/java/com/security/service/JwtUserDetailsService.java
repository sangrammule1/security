package com.security.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.repo.IUserRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

	private String TAG_NAME = "JwtUserDetailsService";

	@Autowired
	IUserRepo iUserRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LOGGER.info(TAG_NAME + " :: inside loadUserByUsername : username :: " + username);

		com.security.model.User mUser = iUserRepo.findByEmail(username);
		if (mUser != null) {
			return new User(mUser.getEmail(), mUser.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}