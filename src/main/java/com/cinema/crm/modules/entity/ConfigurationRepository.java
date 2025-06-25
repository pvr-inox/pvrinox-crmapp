package com.cinema.crm.modules.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	
	Configuration findByName(String name);
	

}
