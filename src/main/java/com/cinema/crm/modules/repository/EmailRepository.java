package com.cinema.crm.modules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.modules.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

	List<Email> findByBookingid(String bookingId);

	List<Email> findByBookingidAndTypeIn(String bookingId, List<String> string);

    
}