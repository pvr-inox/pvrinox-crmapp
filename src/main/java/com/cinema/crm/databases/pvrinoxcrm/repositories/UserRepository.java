package com.cinema.crm.databases.pvrinoxcrm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinoxcrm.entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Boolean existsByUserId(Integer id);
	
	Boolean existsByEmailOrMobile(String email, Integer mobile);
	
	Boolean existsByUserRoleAndStatus(String role,boolean status);
	
	Users findByUserId(Integer id);
	
	Page<Users> findAll(Pageable pageable);


}
