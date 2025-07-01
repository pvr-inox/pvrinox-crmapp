package com.cinema.crm.databases.pvrinoxcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinoxcrm.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
	
	Boolean existsByRoleName(String role);
	
	Roles findByRoleName(String roleName);

}
