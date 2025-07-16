package com.cinema.crm.modules.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.modules.model.ReqModule;
import com.cinema.crm.modules.model.ReqRole;
import com.cinema.crm.modules.model.ReqUser;
import com.cinema.crm.modules.service.UserManagementService;

import jakarta.validation.Valid;

/**
 * This @class is used to manage user, module and role.
 * @author sagar.gaikwad
 * @version 1.0
 */

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
	public ResponseEntity<Object> getModule(@RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "10") int size){
		return userManagementService.getModule(page,size);
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
	public ResponseEntity<Object> createUser(@Valid @RequestBody ReqUser request){
		return userManagementService.createUser(request);
	}
	
	@GetMapping(value = "getUser")
	public ResponseEntity<Object> getUser(@RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "10") int size){
		return userManagementService.getUser(page,size);
	}

}
