package com.okane.domain.services;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.okane.domain.component.PasswordEncoder;
import com.okane.domain.entity.User;
import com.okane.domain.entity.UserRole;
import com.okane.domian.repository.RoleRepository;
import com.okane.domian.repository.UserRepository;
import com.okane.user.RegisterForm;
import com.okane.user.UpdateForm;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public void createUser(RegisterForm RegisterFrom) throws NoSuchAlgorithmException {
		Date nowDate = Calendar.getInstance().getTime();
		User user = new User();
		user.setName(RegisterFrom.getName());
		user.setEmail(RegisterFrom.getEmail());
		user.setPassword(passwordEncoder.hashMD5(RegisterFrom.getPassword()));
		user.setCreated(nowDate);
		user.setActive(nowDate);
		user.setSalary(RegisterFrom.getSalary());
		user.setReserve(RegisterFrom.getReserve());
		userRepository.save(user);

		UserRole userRole = new UserRole();
		userRole.setUser_id(user.getId());
		userRole.setRole_id(1);
		roleRepository.save(userRole);
	}

	public void updateUser(UpdateForm UpdateFrom, Principal principal) throws NoSuchAlgorithmException {
		User user = findOne(principal);
		user.setName(UpdateFrom.getName());
		user.setPassword(passwordEncoder.hashMD5(UpdateFrom.getPassword()));
		user.setSalary(UpdateFrom.getSalary());
		userRepository.save(user);
	}

	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public void deleteUser(int userId) {
		userRepository.delete(userId);
	}

	public User findOne(Principal principal) {
		if (principal == null) {
			return null;
		} else {
			Authentication auth = (Authentication) principal;
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			User user = userRepository.findOneByEmail(userDetails.getUsername());
			if (user == null)
				return null;
			else
				return user;
		}
	}
}
