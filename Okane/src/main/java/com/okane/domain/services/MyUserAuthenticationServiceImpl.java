package com.okane.domain.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.okane.domain.entity.Role;
import com.okane.domain.entity.User;
import com.okane.domian.repository.UserRepository;

@Service
public class MyUserAuthenticationServiceImpl implements UserAccountService, UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findOneByEmail(String username) {
		return userRepository.findOneByEmail(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findOneByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
}