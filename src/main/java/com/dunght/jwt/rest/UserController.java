package com.dunght.jwt.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dunght.jwt.entity.User;
import com.dunght.jwt.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<String>("Something wrong with parameter", HttpStatus.BAD_REQUEST);
		}
		try {
			if (userService.findByUser(user.getUsername()) != null) {
				return new ResponseEntity<String>("UserName is exist", HttpStatus.BAD_REQUEST);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.addUser(user);

		} catch (Exception e) {
			// TODO: handle exception
		     return new ResponseEntity<String>(e.toString(),
		             HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
