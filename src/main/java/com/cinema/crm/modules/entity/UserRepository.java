package com.cinema.crm.modules.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Boolean existsByUserId(Integer id);
	
	Users findByUserId(Integer id);

}
