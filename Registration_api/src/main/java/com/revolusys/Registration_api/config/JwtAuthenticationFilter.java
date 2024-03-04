package com.revolusys.Registration_api.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//this class is responsible for validating the token passed in header
//Firstly from header token username is fetched and validated with existing username in database using userDetailsService
// if username exists then it is authenticated and authentication is set
//In this class we write logic to check the token that is coming in header 

@Component   //customized filter for Rest Urls
public class JwtAuthenticationFilter extends OncePerRequestFilter { 
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//      try {
			//      Thread.sleep(500);
		//  } catch (InterruptedException e) {
		//      throw new RuntimeException(e);
		//  }
		//Authorization

		String requestHeader = request.getHeader("Cookie");
		//Bearer 2352345235sdfrsfgsdfsdf
		logger.info(" Header :  {}", requestHeader);
		
		String username = null;
		String token = null;
		
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			//looking good
			token = requestHeader.substring(7);
			try {

				username = this.jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Some changes has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}


		} else 
		{
			logger.info("Invalid Header Value !! ");
		}


		//
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


			//fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {

				//set the authentication        parameters:-userdetails=principal, null=credentials(usally set to null as they are not used for security tokens), userDetails.getAuthorities()=List of granted authorities                                                
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				//set additional details for authentication
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// Set the authentication in the SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authentication); // important step


			} else {
				// If token validation fails, log the information
				logger.info("Validation fails !!");
			}

		}

		// Continue with the filter chain  for further processing of the request
		filterChain.doFilter(request, response);

	}

}


