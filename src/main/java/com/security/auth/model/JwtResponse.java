package com.security.auth.model;

import java.io.Serializable;

/**
 * @author sangram
 *
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String userId;
	private String userName;
	private String email;
	private String contact;

	/**
	 * @param jwttoken
	 * @param userId
	 * @param userName
	 */
	public JwtResponse(String jwttoken, String userId, String userName) {
		super();
		this.jwttoken = jwttoken;
		this.userId = userId;
		this.userName = userName;
	}

	public String getToken() {
		return this.jwttoken;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}