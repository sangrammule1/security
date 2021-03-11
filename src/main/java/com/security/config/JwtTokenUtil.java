package com.security.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.security.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private String secret = "$@ngr@m";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails, User mUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userPkid", mUser.getUserId());
		
		claims.put("email", mUser.getEmail());
		claims.put("contact", mUser.getContact());
		// claims.put("landingUrl",aCustomer.getOrgLandingPageURL());
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	/*
		// no exprie token for wearable
		public String doGenerateNonExpireToken(DeviceRegistration aDeviceRegistration) {
			Map<String, Object> claims = new HashMap<>();
			claims.put("customerId", aDeviceRegistration.getCustomerId());
			claims.put("deviceId", aDeviceRegistration.getUuid());
			// claims.put("userId", aDeviceRegistration.getUserId());
			return Jwts.builder().setClaims(claims).setSubject(aDeviceRegistration.getUserId())
					.setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS512, secret).compact();
		}*/

	public Claims getClaimsFromToken(String token) {
		return getAllClaimsFromToken(token);
	}

}
