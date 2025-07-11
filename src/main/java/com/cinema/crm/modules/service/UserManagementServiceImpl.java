package com.cinema.crm.modules.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.databases.pvrinoxcrm.entities.Modules;
import com.cinema.crm.databases.pvrinoxcrm.entities.Roles;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.databases.pvrinoxcrm.repositories.ModulesRepository;
import com.cinema.crm.databases.pvrinoxcrm.repositories.RoleRepository;
import com.cinema.crm.databases.pvrinoxcrm.repositories.UserRepository;
import com.cinema.crm.modules.model.ReqModule;
import com.cinema.crm.modules.model.ReqRole;
import com.cinema.crm.modules.model.ReqUser;
import com.cinema.crm.modules.model.WSReturnObj;

import lombok.extern.log4j.Log4j2;

/**
 * This @class is used to manage user, module and role.
 * @author sagar.gaikwad
 * @version 1.0
 */

@Log4j2
@Service
public class UserManagementServiceImpl implements UserManagementService{
	
	private ModulesRepository modulesRepository;
	private RoleRepository roleRepository;
	private UserRepository userRepository;
	
	public UserManagementServiceImpl(ModulesRepository modulesRepository, RoleRepository roleRepository,
			UserRepository userRepository) {
		this.modulesRepository = modulesRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ResponseEntity<Object> createModule(ReqModule request) {
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		try {
			if(!request.getModules().isEmpty()) {
				int lastUnderscore = request.getModules().get(0).lastIndexOf("_");
				String mainModuleName = (lastUnderscore != -1) ? request.getModules().get(0).substring(0, lastUnderscore) : request.getModules().get(0);
				if(request.isUpdate()) {
					modulesRepository.deleteByMainModuleName(mainModuleName);
				}else{
					Boolean exist = modulesRepository.existsByMainModuleName(mainModuleName);
					if(exist) {
						returnObj = WSReturnObj.builder().msg("error").output("Module Name Already Exist. Please Try To Update The Module.").responseCode(204).result("error").build();
						return ResponseEntity.ok(returnObj);
					}
				}
				List<Modules> modulesList = new ArrayList<>();
				for(String name : request.getModules()) {
					Modules module = new Modules();
					module.setModuleName(name);
					module.setMainModuleName(mainModuleName);
					modulesList.add(module);
				}
				modulesRepository.saveAll(modulesList);
				returnObj = WSReturnObj.builder().msg("success").output("Module Created Sucessfully.").responseCode(200).result("sucess").build();
				return ResponseEntity.ok(returnObj);
			}
			returnObj = WSReturnObj.builder().msg("error").output("Module Name Can Not Be Empty.").responseCode(204).result("error").build();
			return ResponseEntity.ok(returnObj);
		} catch (Exception e) {
			log.error("Exception createModule {} : ",e);
			returnObj = WSReturnObj.builder().msg("error").output("Error Occured Failed To Create Module").responseCode(500).result("error").build();
			return ResponseEntity.ok(returnObj);
		}
	}
	
	@Override
	public ResponseEntity<Object> getModule(int page, int size){
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		 Pageable pageable = PageRequest.of(page, size);
		 Page<Modules> modulePage = modulesRepository.findAll(pageable);
		returnObj = WSReturnObj.builder().msg("success").output(modulePage.getContent()).responseCode(200).result("sucess").build();
		return ResponseEntity.ok(returnObj);
	}
	
	@Override
	public ResponseEntity<Object> createRole(ReqRole request){
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		try {
		Boolean exist = roleRepository.existsByRoleName(request.getRoleName());
		if (exist) {
			if (request.isUpdate()) {
				Roles roles = roleRepository.findByRoleName(request.getRoleName());
				if(request.status == false) {
				boolean userExist =	userRepository.existsByRoleAndStatus(request.getRoleName(), true);
					if(userExist) {
						returnObj = WSReturnObj.builder().msg("error").output("This Role Is Assigned To Active User.").responseCode(204).result("error").build();
						return ResponseEntity.ok(returnObj);
					}
				}
				roles.setModules(request.getModuleNames());
				roles.setStatus(request.status);
				roleRepository.save(roles);
				returnObj = WSReturnObj.builder().msg("success").output("Role Updated Successfully.").responseCode(200).result("sucess").build();
				return ResponseEntity.ok(returnObj);
			} else {
				returnObj = WSReturnObj.builder().msg("error").output("Role Already Exist").responseCode(204).result("error").build();
				return ResponseEntity.ok(returnObj);
			}
		} else {
			Roles roles = new Roles();
			roles.setRoleName(request.getRoleName());
			roles.setModules(request.getModuleNames());
			roles.setStatus(true);
			roleRepository.save(roles);
			returnObj = WSReturnObj.builder().msg("success").output("Role Created Successfully.").responseCode(200).result("sucess").build();
			return ResponseEntity.ok(returnObj);
		}
		} catch (Exception e) {
			log.error("Exception createModule {} : ",e);
			returnObj = WSReturnObj.builder().msg("error").output("Error Occured Failed To Create Module").responseCode(500).result("error").build();
			return ResponseEntity.ok(returnObj);
		}
	}
	
	@Override
	public ResponseEntity<Object> getRole(){
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		List<Roles> roles = roleRepository.findAll();
		returnObj = WSReturnObj.builder().msg("success").output(roles).responseCode(200).result("sucess").build();
		return ResponseEntity.ok(returnObj);
	}
	
	@Override
	public ResponseEntity<Object> createUser(ReqUser request){
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		try {
		boolean userExist = userRepository.existsByEmailOrMobile(request.getEmail(),request.getMobile());
		if(userExist) {
			if(request.isUpdate()) {
				Users users = userRepository.findByUserId(request.getUserId());
				users.setEmail(request.getEmail());
				users.setMobile(request.getMobile());
				users.setName(request.getName());
				users.setRole(request.getUserRole());
				users.setStatus(request.isStatus());
				users.setPassword(request.getPassword());
				userRepository.save(users);
				returnObj = WSReturnObj.builder().msg("success").output("User Updated Successfully.").responseCode(204).result("success").build();
				return ResponseEntity.ok(returnObj);
			}else {
				returnObj = WSReturnObj.builder().msg("error").output("User Already Exist.").responseCode(204).result("error").build();
				return ResponseEntity.ok(returnObj);
			}
		}else {
			Users users = Users.builder().name(request.getName()).email(request.getEmail()).mobile(request.getMobile()).role(request.getUserRole()).status(request.isStatus()).build();
			userRepository.save(users);
			returnObj = WSReturnObj.builder().msg("success").output("User Created Successfully.").responseCode(200).result("sucess").build();
			return ResponseEntity.ok(returnObj);
		}
		} catch (Exception e) {
			log.error("Exception createModule {} : ",e);
			returnObj = WSReturnObj.builder().msg("error").output("Error Occured Failed To Create Module").responseCode(500).result("error").build();
			return ResponseEntity.ok(returnObj);
		}
	}
	
	@Override
	public ResponseEntity<Object> getUser(int page, int size){
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		 Pageable pageable = PageRequest.of(page, size);
		 Page<Users> usersPage = userRepository.findAll(pageable);
		returnObj = WSReturnObj.builder().msg("success").output(usersPage.getContent()).responseCode(200).result("sucess").build();
		return ResponseEntity.ok(returnObj);
	}

}
