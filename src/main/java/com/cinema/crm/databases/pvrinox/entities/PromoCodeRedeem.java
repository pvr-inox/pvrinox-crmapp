package com.cinema.crm.databases.pvrinox.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "promocode_redeem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeRedeem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
	private Long id; 
	@Column(name = "bookingid", length = 20)
	private String orderIdEx = "";
	private int orderId;
	@Column(length = 15) private String bookType = "";
	@Column(length = 15) private String filmCode = "";
	@Column(length = 60) private String email;
	@Column(length = 15) private String mobile;
	@Column(length = 60) private String name;
	private Long userId; 
	@Column(length = 25) private String platform;
	@Column(length = 15) private String status = "";
	@Column(length = 100) private String coupon = "";
	@Column(length = 30) private String couponType = "";
	@Column(length = 10) private int couponId;
	@Temporal(TemporalType.TIMESTAMP) private Date redeemDate;
	private int lngTransId;
	@Column(name = "amount") private Long amount;
	@Column(name = "blnSuccess") private boolean blnSuccess;
	@Column(columnDefinition = "TEXT") private String strException = "";
	@Column(length = 100) private String strComments = "";
	@Column(length = 100) private String strRewards = "";
	@Column(length = 30) private String transType = "";
	@Column(length = 30, columnDefinition = "varchar(50) default ''") private String maskedCardNumber = "";
	
	public PromoCodeRedeem(int orderId, String orderIdEx, String bookType, boolean blnSuccess, String strException,
			String strComments, String strRewards, String coupon, String status, Long amount, Date redeemDate, String platform, 
			Long userId, int couponId, String couponType, String transType) {
		super();
		this.orderId = orderId;
		this.orderIdEx = orderIdEx;
		this.bookType = bookType;
		this.blnSuccess = blnSuccess;
		this.strException = strException;
		this.strComments = strComments;
		this.strRewards = strRewards;
		this.coupon = coupon;
		this.status = status;
		this.amount = amount;
		this.redeemDate = redeemDate;
		this.platform = platform;
		this.userId = userId;
		this.couponId = couponId;
		this.couponType = couponType;
		this.transType = transType;
	}

}
