package com.cinema.crm.databases.pvrinoxcrm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinema.crm.databases.pvrinoxcrm.entities.RefundDetails;


public interface RefundDetailsRepository extends JpaRepository<RefundDetails, Long> {

	Optional<RefundDetails> findByBookingId(String bookingId);
	@Modifying
	@Query(value = "update refund_details SET rrn_number = :rrn where booking_id = :booking",nativeQuery = true)
	void updateRrn(@Param("rrn") String rrn,@Param("booking") String booking);
	
	List<RefundDetails> findByisRefundedFalse();
}
