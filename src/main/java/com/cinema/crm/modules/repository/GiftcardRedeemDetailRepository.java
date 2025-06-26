package com.cinema.crm.modules.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.modules.database.main.GiftcardRedeemDetail;

public interface GiftcardRedeemDetailRepository extends JpaRepository<GiftcardRedeemDetail, Long> {
	
	GiftcardRedeemDetail findByBookingId(String bookingId);
	
	

}
	