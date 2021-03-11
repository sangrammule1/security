package com.security.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TokenInfo {
	
	private String userId;

	private String userPkId;

}
