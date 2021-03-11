
package com.security.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.config.JwtTokenUtil;
import com.security.model.TokenInfo;

import io.jsonwebtoken.Claims;

@Component
public class LoginFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

	private String TAG_NAME = "LoginFilter";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	TokenInfo tokenInfo;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info(TAG_NAME + " :: inside doFilter ");
		// TODO Auto-generated method stub
		String mPath = ((HttpServletRequest) request).getServletPath();
		LOGGER.info(TAG_NAME + " :: inside doFilter : currentPath :: " + mPath);
		String jwtToken = ((HttpServletRequest) request).getHeader("Authorization");

		if (!mPath.contains("authenticate") && jwtToken != null) {
			jwtToken = jwtToken.substring(7);
			Claims mClaims = jwtTokenUtil.getClaimsFromToken(jwtToken);
			tokenInfo.setUserId(mClaims.get("email").toString());
			tokenInfo.setUserPkId((mClaims.get("userPkid").toString()));
		}
		chain.doFilter(request, response);
	};

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
