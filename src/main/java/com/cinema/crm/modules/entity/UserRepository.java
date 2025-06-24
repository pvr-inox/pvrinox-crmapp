package com.cinema.crm.modules.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Boolean existsByUserId(Integer id);
	
	Boolean existsByEmailOrMobile(String email, Integer mobile);
	
	Boolean existsByUserRoleAndStatus(String role,boolean status);
	
	Users findByUserId(Integer id);
	
	Page<Users> findAll(Pageable pageable);


}
