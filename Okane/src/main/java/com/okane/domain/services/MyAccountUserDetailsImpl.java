package com.okane.domain.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.okane.domain.entity.Role;
import com.okane.domain.entity.User;



public class MyAccountUserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private User user;

	public MyAccountUserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<Role> userRoles = user.getRoles();
		for (Role role : userRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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