package com.cinema.crm.databases.pvrinox.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinema.crm.databases.pvrinox.entities.OrderBooking;


public interface OrderBookingRepository extends JpaRepository<OrderBooking, String>{
	
	Optional<OrderBooking> findById(String id);
	
	@Query("SELECT o FROM OrderBooking o WHERE o.id = :bookingId")
	OrderBooking findOrder(@Param("bookingId") String bookingId);
	
	@Query("SELECT o FROM OrderBooking o WHERE o.orderTicket.sessionId = :sessionId")
	List<OrderBooking> findByOrderTicketSessionId(@Param("sessionId") long sessionId);

}
