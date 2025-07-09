package com.cinema.crm.databases.pvrinoxcrm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinoxcrm.entities.RefundDetails;


public interface RefundDetailsRepository extends JpaRepository<RefundDetails, Long> {

	Optional<RefundDetails> findByBookingId(String bookingId);
	
}
