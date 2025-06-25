package com.cinema.crm.modules.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
	
	Boolean existsByRoleName(String role);
	
	Roles findByRoleName(String roleName);

}
