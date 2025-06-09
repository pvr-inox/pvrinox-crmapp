package com.cinema.crm.modules.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderBookingRepository extends JpaRepository<OrderBooking, String>{
	
	Optional<OrderBooking> findById(String id);
	
	@Query("SELECT o FROM OrderBooking o WHERE o.orderTicket.sessionId = :sessionId")
	List<OrderBooking> findByOrderTicketSessionId(@Param("sessionId") long sessionId);

}
