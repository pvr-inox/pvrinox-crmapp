package com.cinema.crm.login.service;

import com.cinema.crm.constants.GenericResponse;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.login.model.LoggedInResponse;

public interface UsersService {
	public GenericResponse register(Users users);

	public LoggedInResponse verify(String username, String userPassword);

}
