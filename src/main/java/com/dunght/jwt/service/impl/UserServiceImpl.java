package com.dunght.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dunght.jwt.entity.User;
import com.dunght.jwt.repository.UserRepository;
import com.dunght.jwt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User addUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User findByUser(String username) throws Exception {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

}
