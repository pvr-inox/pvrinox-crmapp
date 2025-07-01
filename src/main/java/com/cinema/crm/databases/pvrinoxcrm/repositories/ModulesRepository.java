package com.cinema.crm.databases.pvrinoxcrm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinoxcrm.entities.Modules;

public interface ModulesRepository extends JpaRepository<Modules, Integer> {
	
	Boolean existsByMainModuleName(String name);
	
	void  deleteByMainModuleName(String name);
	
	Page<Modules> findAll(Pageable pageable);

}
