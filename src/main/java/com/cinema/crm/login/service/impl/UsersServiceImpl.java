package com.cinema.crm.login.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.constants.GenericResponse;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.databases.pvrinoxcrm.repositories.UserRepository;
import com.cinema.crm.login.jwt.JwtService;
import com.cinema.crm.login.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UserRepository userRepository;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public GenericResponse register(Users users) {
		users.setPassword(encoder.encode(users.getPassword()));
		Users foundedUser = userRepository.findByEmail(users.getEmail());
		if(Objects.nonNull(foundedUser))
			return GenericResponse.builder().result(Result.FAILED).responseCode(Constants.RespCode.FAILED).message(Constants.Message.USER_ALREADY_EXIST).build();
		
		Users registered = userRepository.save(users);
		if(registered.getUserId() != null)
			return GenericResponse.builder().result(Result.SUCCESS).responseCode(Constants.RespCode.SUCCESS).message(Constants.Message.USER_REGISTERED_SUCCESSFULLY).build();
		else
			return GenericResponse.builder().result(Result.FAILED).responseCode(Constants.RespCode.FAILED).message(Constants.Message.USER_REGISTRATION_FAILED).build();
	}

	@Override
	public String verify(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(username);
		}
		return Constants.Message.FAILED_TO_LOGGED_IN;
	}

}
