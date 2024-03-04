package com.revolusys.Registration_api.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.revolusys.Registration_api.entities.Register;

public class CustomUser implements UserDetails{
	
	@Autowired
	private Register reg;
	
	public CustomUser(Register reg)
	{
		super();
		this.reg=reg;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
		
		return reg.getPassword() ;
	}

	@Override
	public String getUsername() {
		
		return reg.getEmail() ; //important 
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
