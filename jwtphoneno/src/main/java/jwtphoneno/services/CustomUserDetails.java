package jwtphoneno.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jwtphoneno.entity.UserCredential;

public class CustomUserDetails implements UserDetails{

	@Autowired
	private UserCredential userCredential‎;
	
	public CustomUserDetails(UserCredential userCredential)
	{
		super();
		this.userCredential‎=userCredential;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return null;
	}

	@Override
	public String getPassword() {
		
		return userCredential‎.getPassword();
	}
/*
	@Override
	public String getUsername() {
		
		return userCredential‎.getPhoneno();
	}
	*/
	@Override
	public String getUsername() {
		
		return userCredential‎.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
