package com.cinema.crm.databases.pvrinox.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.GyftrRedeemDetail;
@Repository
public interface GyftrRedeemDetailRepository extends JpaRepository<GyftrRedeemDetail, Long> {
	
	@Query(value = "SELECT SUM(gyftr.gyftr_amount) FROM gyftr_redeem_details gyftr WHERE gyftr.status =:status", nativeQuery = true)
	Optional<Long> totalDiscount(@Param("status") String status);
	
	Optional<GyftrRedeemDetail> findByBookingIdAndCouponAndStatus(String bookingId, String coupon, String status);
	
	List<GyftrRedeemDetail>	findAllByBookingId(String bookingId);
	List<GyftrRedeemDetail> findAllByBookingIdAndStatus(String orderIdEx, String status);

}
