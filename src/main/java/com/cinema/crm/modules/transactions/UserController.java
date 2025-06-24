package com.cinema.crm.modules.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.modules.model.ReqModule;
import com.cinema.crm.modules.model.ReqRole;
import com.cinema.crm.modules.model.ReqUser;
import com.cinema.crm.modules.service.UserManagementService;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
	
	private final UserManagementService userManagementService;

	public UserController(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}
	
	@PostMapping(value  = "createModule")
	public ResponseEntity<Object> createModule(@RequestBody ReqModule request){
		return userManagementService.createModule(request);
	}
	
	@GetMapping(value = "getModule")
	public ResponseEntity<Object> getModule(){
		return userManagementService.getModule();
	}
	
	@PostMapping(value = "createRole")
	public ResponseEntity<Object> createRole(@RequestBody ReqRole request){
		return userManagementService.createRole(request);
	}
	
	@GetMapping(value = "getRoles")
	public ResponseEntity<Object> getRole(){
		return userManagementService.getRole();
	}
	
	@PostMapping(value = "createUser")
	public ResponseEntity<Object> createUser(@RequestBody ReqUser request){
		return userManagementService.createUser(request);
	}
	
	@GetMapping(value = "getUser")
	public ResponseEntity<Object> getUser(){
		return userManagementService.getUser();
	}

}
