package com.cinema.crm.databases.pvrinox.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinox.entities.PromoCodeRedeem;

public interface PromoCodeRedeemRepository extends JpaRepository<PromoCodeRedeem, Long>{
	
	Optional<PromoCodeRedeem> findByOrderIdEx(String id);

}
