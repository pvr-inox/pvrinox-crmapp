package com.cinema.crm.modules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.modules.entity.JuspayRedeemDetail;

@Repository
public interface JuspayRedeemDetailRepository extends JpaRepository<JuspayRedeemDetail, String> {
	
	JuspayRedeemDetail findByBookingidAndStatus(String bookingid,String status);
	List<JuspayRedeemDetail> findAllByBookingid(String bookingid);

}
