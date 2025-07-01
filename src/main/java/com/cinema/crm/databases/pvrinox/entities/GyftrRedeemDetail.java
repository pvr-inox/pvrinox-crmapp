package com.cinema.crm.databases.pvrinox.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "gyftr_redeem_details")
public class GyftrRedeemDetail {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String transactionId;
	private String bookingId;
	private String bookType;
	private String coupon;
	private String status;
	private long amount;
	private long userId;
	@Temporal(TemporalType.TIMESTAMP) private Date redeemDate;
	private String platform;
	private String responseMessage;
	@Column(columnDefinition = "BIGINT default 0") private long gyftrAmount;
	
	public GyftrRedeemDetail(String transactionId, String bookingId, String bookType, String coupon, String status,
			long amount, long userId, Date redeemDate, String platform) {
		super();
		this.transactionId = transactionId;
		this.bookingId = bookingId;
		this.bookType = bookType;
		this.coupon = coupon;
		this.status = status;
		this.amount = amount;
		this.userId = userId;
		this.redeemDate = redeemDate;
		this.platform = platform;
	}
	
	
}
