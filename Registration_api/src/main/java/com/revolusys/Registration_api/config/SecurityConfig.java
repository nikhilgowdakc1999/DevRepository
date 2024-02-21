package com.revolusys.Registration_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.revolusys.Registration_api.service.CustomUserDetailService;

//main class responsible for configuring spring security

@Configuration
@EnableWebSecurity // helps to configure spring security related beans such as SecurityFilterChain
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService getUserDetailsService()
	{
		return new CustomUserDetailService();
	}

	@Bean
	JwtAuthenticationFilter filter()
	{
		return new JwtAuthenticationFilter();
	}

	@Bean // DaoAuthenticationProvider is a type of AuthenticationProvider which looks up UserDetails from UserDetailsService and AuthenticationProvider is the most common implementation of AuthenticationManager
	DaoAuthenticationProvider doDaoAuthenticationProvider() 
	{
		DaoAuthenticationProvider Provider=new DaoAuthenticationProvider();
		Provider.setUserDetailsService(getUserDetailsService()); 
		Provider.setPasswordEncoder(passwordEncoder());  //validates password on UserDetails returned in previous step
		return Provider;	
	}

	@Bean //AuthenticationManager is an API defines how Spring Security's Filters perform authentication
	AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception
	{
		return builder.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf->csrf.disable()) //Cross site request forgery: most severe vulnerabilities like changing user info or gaining full info about user
		 //   .cors(cors->cors.disable()) //Cross origin resource sharing: Mechanism of sharing data to third party intentionally
		// cors should not disabled because there will be no connection with frontend team
		.authorizeHttpRequests(auth->auth.requestMatchers("/FetchRecords","/DeleteRecords","/ForgotPassword").authenticated().requestMatchers("/SaveRecord","/Auth-Login").permitAll().anyRequest().authenticated())
		.exceptionHandling(ex-> ex.authenticationEntryPoint(point))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//won't store any info about log-in user in memory

		http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class); //our customized filter is called before UsernamePasswordAuthenticationFilter(used for /login) just to ensure that order of SecurityFilterChain is maintained
		return http.build();
	}

}
