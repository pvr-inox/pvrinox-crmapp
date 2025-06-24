package com.cinema.crm.modules.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulesRepository extends JpaRepository<Modules, Integer> {
	
	Boolean existsByMainModuleName(String name);
	
	void  deleteByMainModuleName(String name);

}
