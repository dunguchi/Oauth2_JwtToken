package com.dunght.jwt.service;

import com.dunght.jwt.entity.User;

public interface UserService {
	User addUser(User user) throws Exception;
	
	User findByUser(String username) throws Exception;
}
