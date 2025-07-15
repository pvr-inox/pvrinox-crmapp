package com.cinema.crm.login.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.constants.GenericResponse;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.login.model.LoggedInResponse;
import com.cinema.crm.login.service.UsersService;

@RestController
@RequestMapping("/api/auth")
public class LoginApi {

	@Autowired
	private UsersService usersService;
	
	@PostMapping("/register")
	public ResponseEntity<GenericResponse> register(@RequestBody Users users) {
		GenericResponse response = usersService.register(users);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoggedInResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password) {
		return ResponseEntity.ok(usersService.verify(username,password));
	}
	
}
