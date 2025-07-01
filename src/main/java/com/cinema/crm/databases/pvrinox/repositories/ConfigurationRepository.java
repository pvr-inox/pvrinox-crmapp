package com.cinema.crm.databases.pvrinox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	
	Configuration findByName(String name);
	

}
