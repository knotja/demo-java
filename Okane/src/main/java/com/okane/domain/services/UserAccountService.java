package com.okane.domain.services;

import com.okane.domain.entity.User;

public interface UserAccountService {
	User findOneByEmail(String username);
}