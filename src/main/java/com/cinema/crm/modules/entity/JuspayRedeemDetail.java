package com.cinema.crm.modules.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "juspay_redeem_details")
public class JuspayRedeemDetail {

	@Id private String id = "";
	@Column(length = 30) private String bookingid = "";
	@Column(length = 50) private String mid = "";
	@Column(length = 20) private String booktype = "";

	private long userId;
	@Column(length = 20) private String mobile;
	@Column(length = 50) private String email;
	@Column(length = 15) private String platform;

	@Column(length = 10) private String amount = "";
	@Column(length = 20) private String status = "";
	@Temporal(TemporalType.TIMESTAMP) private Date redeemDate;

	@Column(length = 100) private String transactionId;
	@Column(length = 30) private String transactionStatus = "";
	@Column(length = 5) private String responseCode = "";
	@Column(length = 50) private String rrn = "";
	@Column(length = 30) private String authId = "";
	@Column(columnDefinition = "TEXT") private String responseMessage = "";

	@Column(length = 50) private String paymentMethod = "";
	@Column(length = 50) private String paymentMethodType = "";

	@Column(length = 100) private String tnxUuid = "";
	@Column(length = 500) private String gateway = "";
	@Column(length = 100) private String epgTxnId = "";

	@Column(length = 20) private String cardType = ""; 	
	@Column(length = 100) private String cardHolderName = "";
	@Column(length = 50) private String cardIssuer = "";
	@Column(length = 20) private String cardBrand = "";

	private boolean refunded = false;
	private String refundAmount = "0";
	@Column(length = 100) private String refundId = "";
	private Date refundDate;
	
	@Column(length = 30) private String errorCode = "";
	@Column(columnDefinition = "TEXT") private String errorMessage = "";
	
	@Column(columnDefinition = "varchar(200) default ''") private String mandateId = "";
	@Column(columnDefinition = "varchar(200) default ''") private String mandateStatus = "";
	
	@Column(columnDefinition = "varchar(255) default ''") private String payerAppName = "";
	@Column(columnDefinition = "varchar(10) default ''") private String cardBin = "";
	@Column(columnDefinition = "varchar(10) default ''") private String lastFourDigits = "";
	
	public JuspayRedeemDetail(String id, String bookingid, String booktype, long userId, String mobile,
			String email, String platform, String amount, String status, Date redeemDate) {
		super();
		this.id = id;
		this.bookingid = bookingid;
		this.booktype = booktype;
		this.userId = userId;
		this.mobile = mobile;
		this.email = email;
		this.platform = platform;
		this.amount = amount;
		this.status = status;
		this.redeemDate = redeemDate;
	}
}

