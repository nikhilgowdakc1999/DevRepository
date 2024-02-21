package com.revolusys.Registration_api.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// class mainly used for handling Authentication Exception for unauthenticated user request
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override // commence method will indicate browser its credentials are no longer Authorized
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException 
	{
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        PrintWriter writer = response.getWriter();
	        writer.println("Access Denied !! " + authException.getMessage());
		
	}

}
