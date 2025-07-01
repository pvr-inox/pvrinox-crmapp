package com.cinema.crm.databases.pvrinox.entities;

import java.util.Date;

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
@Entity
@NoArgsConstructor
@Table(name = "giftcard_redeem_details")
public class GiftcardRedeemDetail {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
	private String bookingId;
	private String bookType;
	private String cardNumber;
	private String invoiceNumber;
	private long amount;
	private long batchNumber;
	private String approvalCode;
	private String status;
	private long userId;
	@Temporal(TemporalType.TIMESTAMP) private Date redeemDate;
	private String platform;
	private String payType;
	private String transactionId;
	private String customerMobile;
	private String customerEmail;
	
	public GiftcardRedeemDetail(String bookingId, String bookType, String cardNumber, String invoiceNumber, long amount,
			long batchNumber, String approvalCode, String status, long userId,String customerMobile,String customerEmail, Date redeemDate, String platform, String payType) {
		super();
		this.bookingId = bookingId;
		this.bookType = bookType;
		this.cardNumber = cardNumber;
		this.invoiceNumber = invoiceNumber;
		this.amount = amount;
		this.batchNumber = batchNumber;
		this.approvalCode = approvalCode;
		this.status = status;
		this.userId = userId;
		this.redeemDate = redeemDate;
		this.platform = platform;
		this.payType = payType;
		this.customerEmail=customerEmail;
		this.customerMobile=customerMobile;
	}
}

