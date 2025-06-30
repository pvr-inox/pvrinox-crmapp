package com.cinema.crm.databases.pvrinox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinox.entities.GiftcardRedeemDetail;

public interface GiftcardRedeemDetailRepository extends JpaRepository<GiftcardRedeemDetail, Long> {
	
	GiftcardRedeemDetail findByBookingId(String bookingId);
	
	

}
	