package com.security.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.auth.model.JwtRequest;
import com.security.auth.model.JwtResponse;
import com.security.config.JwtTokenUtil;
import com.security.model.User;
import com.security.repo.IUserRepo;

@RestController
public class JwtAuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

	private String TAG_NAME = "JwtAuthenticationController";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private IUserRepo iUserRepo;

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		LOGGER.info(TAG_NAME + " :: inside generateAuthenticationToken : user name :: "
				+ authenticationRequest.getUsername());
		Objects.requireNonNull(authenticationRequest.getUsername());
		Objects.requireNonNull(authenticationRequest.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		User mUser = iUserRepo.findByEmail(authenticationRequest.getUsername());// find by ph or email
		JwtResponse mJwtResponse = null;

		if (mUser != null) {
			if (!mUser.getPassword().equals(authenticationRequest.getPassword())) {
				LOGGER.error(TAG_NAME + " :: inside generateAuthenticationToken : user auth fail!");
				return new ResponseEntity<String>("Failed", HttpStatus.UNAUTHORIZED);
			}

			// generate token
			final String token = jwtTokenUtil.generateToken(userDetails, mUser);
			mJwtResponse = new JwtResponse(token, mUser.getEmail(), mUser.getUserName());

			mJwtResponse.setEmail(mUser.getEmail());
			mJwtResponse.setContact(mUser.getContact());
		}

		return ResponseEntity.ok(mJwtResponse);
	}

	@GetMapping("/security/welcome/{name}")
	public ResponseEntity<String> welcomeMsg(@PathVariable("name") String name) {
		return new ResponseEntity<String>("Welcome to Spring Security " + name, HttpStatus.OK);
	}

}
