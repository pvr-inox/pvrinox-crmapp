package com.cinema.crm.databases.pvrinox.entities;

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
	@Column(name="bookingid", length = 30) private String bookingid = "";
	@Column(length = 50) private String mid = "";
	@Column(length = 20) private String booktype = "";

	@Column(name = "user_Id") private long userId;
	@Column(length = 20) private String mobile;
	@Column(length = 50) private String email;
	@Column(length = 15) private String platform;

	@Column(length = 10) private String amount = "";
	@Column(length = 20) private String status = "";
	@Temporal(TemporalType.TIMESTAMP) @Column(name="redeem_date") private Date redeemDate;

	@Column(name = "transaction_id", length = 100) private String transactionId;
	@Column(name = "transaction_status", length = 30) private String transactionStatus = "";
	@Column(name = "response_code", length = 5) private String responseCode = "";
	@Column(length = 50) private String rrn = "";
	@Column(name = "auth_Id" , length = 30) private String authId = "";
	@Column(name = "response_message", columnDefinition = "TEXT") private String responseMessage = "";

	@Column(name = "payment_method", length = 50) private String paymentMethod = "";
	@Column(name = "payment_method_type", length = 50) private String paymentMethodType = "";

	@Column(name = "tnx_uuid", length = 100) private String tnxUuid = "";
	@Column(length = 500) private String gateway = "";
	@Column(name = "epg_txn_id", length = 100) private String epgTxnId = "";

	@Column(name = "card_type", length = 20) private String cardType = ""; 	
	@Column(name = "card_holder_name",  length = 100) private String cardHolderName = "";
	@Column(name = "card_issuer",  length = 50) private String cardIssuer = "";
	@Column(name = "card_brand", length = 20) private String cardBrand = "";

	private boolean refunded = false;
	@Column(name = "refund_amount") private String refundAmount = "0";
	@Column(name = "refund_id", length = 100) private String refundId = "";
	@Column(name = "refund_date") private Date refundDate;
	
	@Column(name = "error_code", length = 30) private String errorCode = "";
	@Column(name = "error_message", columnDefinition = "TEXT") private String errorMessage = "";
	
	@Column(name = "mandate_id", columnDefinition = "varchar(200) default ''") private String mandateId = "";
	@Column(name = "mandate_status", columnDefinition = "varchar(200) default ''") private String mandateStatus = "";
	
	@Column(name = "payer_app_name", columnDefinition = "varchar(255) default ''") private String payerAppName = "";
	@Column(name = "card_bin", columnDefinition = "varchar(10) default ''") private String cardBin = "";
	@Column(name = "last_four_digits", columnDefinition = "varchar(10) default ''") private String lastFourDigits = "";
	
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

