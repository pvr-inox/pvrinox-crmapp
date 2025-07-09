package com.cinema.crm.login.service;

import com.cinema.crm.constants.GenericResponse;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;

public interface UsersService {
	public GenericResponse register(Users users);

	public String verify(String username, String userPassword);

}
