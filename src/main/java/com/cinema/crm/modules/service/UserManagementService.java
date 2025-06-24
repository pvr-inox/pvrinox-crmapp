package com.cinema.crm.modules.service;

import org.springframework.http.ResponseEntity;

import com.cinema.crm.modules.model.ReqModule;
import com.cinema.crm.modules.model.ReqRole;
import com.cinema.crm.modules.model.ReqUser;

public interface UserManagementService {
	
	 ResponseEntity<Object> createModule(ReqModule request);
	
	 ResponseEntity<Object> getModule();
	
	 ResponseEntity<Object> createRole(ReqRole request);
	
	 ResponseEntity<Object> getRole();
	 
	 ResponseEntity<Object> createUser(ReqUser request);
	 
	 ResponseEntity<Object> getUser();

}
