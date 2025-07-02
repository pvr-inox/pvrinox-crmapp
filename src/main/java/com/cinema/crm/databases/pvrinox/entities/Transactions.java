package com.cinema.crm.databases.pvrinox.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_booking")
public class Transactions {

	@Id
	private String id;

//	@Column(name = "book_type")
	private String bookType;
//	@Column(name = "booking_id")
	private String bookingId;
//	@Column(name = "booking_status")
	private String bookingStatus;
//	@Column(name = "booking_time")
	private LocalDateTime bookingTime;
//	@Column(name = "business_date")
	private LocalDate businessDate;
//	@Column(name = "cancel_time")
	private LocalDateTime cancelTime;
//	@Column(name = "chain")
	private String chain;
//	@Column(name = "created_at")
	private LocalDateTime createdAt;
//	@Column(name = "dtm_source")
	private String dtmSource;
//	@Column(name = "email")
	private String email;
//	@Column(name = "food_ref_id")
	private String foodRefId;
//	@Column(name = "ip_address")
	private String ipAddress;
//	@Column(name = "mobile")
	private String mobile;
//	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;
//	@Column(name = "name")
	private String name;
//	@Column(name = "cancel_avail")
	private Boolean cancelAvail;
//	@Column(name = "certificate")
	private String certificate;
//	@Column(name = "city_name")
	private String cityName;
//	@Column(name = "film_id")
	private String filmId;
//	@Column(name = "film_master_id")
	private String filmMasterId;
//	@Column(name = "film_name")
	private String filmName;
//	@Column(name = "format")
	private String format;
//	@Column(name = "genre")
	private String genre;
//	@Column(name = "language")
	private String language;
//	@Column(name = "stategstn")
	private String stategstn;
//	@Column(name = "theater_id")
	private String theaterId;
//	@Column(name = "theater_name")
	private String theaterName;
//	@Column(name = "fb_count")
	private Integer fbCount;
//	@Column(name = "fb_discount")
	private Long fbDiscount;
//	@Column(name = "fb_total_price")
//	private Long fbTotalPrice;
//	@Column(name = "fnb")
	private Boolean fnb;
//	@Column(name = "food_type")
	private String foodType;

	@Column(columnDefinition = "json")
	private String foods;

//	@Column(name = "pick_up_time")
	private String pickUpTime;
//	@Column(name = "order_id")
	private Integer orderId;
//	@Column(name = "adult_seats")
	private Integer adultSeats;
	private String audi;
//	@Column(name = "balance_amount")
	private String balanceAmount;
//	@Column(name = "child_seats")
	private Integer childSeats;
//	@Column(name = "child_ticket_price")
	private Long childTicketPrice;
	private Long conv;
//	@Column(name = "conv_gst")
	private Long convGst;
//	@Column(name = "conv_total")
	private Long convTotal;
//	@Column(name = "cvp")
	private Boolean cvp;
//	@Column(name = "cvp_amount")
	private Long cvpAmount;
//	@Column(name = "cvp_info")
	private String cvpInfo;
//	@Column(name = "cvp_vouchers")
	private String cvpVouchers;
//	@Column(name = "discount")
	private Long discount;
//	@Column(name = "donation")
	private Long donation;
//	@Column(name = "donation_type")
	private String donationType;
//	@Column(name = "end_time")
	private LocalDateTime endTime;
//	@Column(name = "experience")
	private String experience;
//	@Column(name = "num_of_seats")
	private Integer numOfSeats;
//	@Column(name = "passport_ticket_value")
	private Long passportTicketValue;
//	@Column(name = "pgrp_id")
	private String pgrpId;
//	@Column(name = "rate_card")
	private Long rateCard;
//	@Column(name = "screen_id")
	private Integer screenId;
	private String seats;
//	@Column(name = "session_id")
	private Long sessionId;
//	@Column(name = "show_class")
	private String showClass;
//	@Column(name = "show_class_name")
	private String showClassName;
//	@Column(name = "show_time")
	private LocalDateTime showTime;
//	@Column(name = "ticket_price")
	private Long ticketPrice;
//	@Column(name = "total_ticket_price")
	private Long totalTicketPrice;

	@Column( columnDefinition = "text")
	private String vistaException;

//	@Column(name = "vista_state")
	private String vistaState;
//	@Column(name = "vista_time")
	private LocalDateTime vistaTime;
//	@Column(name = "partner_id")
	private String partnerId;
//	@Column(name = "payment_status")
	private String paymentStatus;
//	@Column(name = "paymode")
	private String paymode;
//	@Column(name = "coupon_type")
	private String couponType;
//	@Column(name = "couponused")
	private Boolean couponused;
//	@Column(name = "dccardused")
	private Boolean dccardused;
	private Boolean giftcardused;
	private Boolean gyfterused;
	private Boolean hyattused;
	private Boolean juspayused;
//	@Column(name = "pg_type")
	private String pgType;
	private String platform;
	private String pos;
	private Boolean qr;
//	@Column(name = "trans_id")
	private String transId;
//	@Column(name = "user_id")
	private Long userId;
//	@Column(name = "app_version")
	private String appVersion;
	private Long juspayamt;
//	@Column(name = "platform_cancel")
	private String platformCancel;
	private Boolean razorpayused;
//	@Column(name = "hsn_sac_code")
	private String hsnSacCode;
//	@Column(name = "gst_invoice_no")
	private String gstInvoiceNo;
//	@Column(name = "refunded_amount")
	private Long refundedAmount;
//	@Column(name = "my_ticket")
	private String myTicket;
	@Column(columnDefinition = "TINYINT default 0")
	private boolean additionalLangClip;
//	@Column(name = "gst_invoice_nocan")
	private String gstInvoiceNocan;
//	@Column(name = "gst_invoice_nocn")
	private String gstInvoiceNocn;
//	@Column(name = "ticket_tax")
	private Long ticketTax;

	@Column(columnDefinition = "json")
	private String surcharge;

//	@Column(name = "surcharge_amount")
	private Long surchargeAmount;
//	@Column(name = "pos_ho_code")
	private String posHoCode;
//	@Column(name = "sap_code")
	private String sapCode;

	@Column(columnDefinition = "text")
	private String orderData;

//	@Column(name = "revised_ticket_amount")
	private Long revisedTicketAmount;
//	@Column(name = "instapayused")
	private Boolean instapayused;
//	@Column(name = "eticket_url")
	private String eticketUrl;
//	@Column(name = "cancel_fee")
	private Long cancelFee;
//	@Column(name = "paymode_refund")
	private String paymodeRefund;
//	@Column(name = "event_type")
	private String eventType;
//	@Column(name = "dtm_source_partner")
	private String dtmSourcePartner;
//	@Column(name = "dtm_campaign")
	private String dtmCampaign;
//	@Column(name = "flexi_applicable")
	private Boolean flexiApplicable;
//	@Column(name = "flexi_apply")
	private Boolean flexiApply;
//	@Column(name = "flexi_ticket_price")
	private Long flexiTicketPrice;
//	@Column(name = "tickets_refunded")
	private Long ticketsRefunded;

	@Column(columnDefinition = "json")
	private String ticketInvoiceData;

//	@Column(name = "family_bundle_charge")
	private Long familyBundleCharge;
//	@Column(name = "family_bundle")
	private Boolean familyBundle;
//	@Column(name = "family_bundle_amount")
	private Long familyBundleAmount;
//	@Column(name = "family_bundle_info")
	private String familyBundleInfo;
//	@Column(name = "ref_code")
	private String refCode;
//	@Column(name = "show_order_id")
	private String showOrderId;
//	@Column(name = "source")
	private String source;
//	@Column(name = "user_type")
	private String userType;

	@Column(columnDefinition = "json")
	private String fnbDeliveryStatus;

//	@Column(name = "delivery_mode")
	private String deliveryMode;
//	@Column(name = "super_ticket")
	private Boolean superTicket;
//	@Column(name = "super_ticket_vouchers")
	private String superTicketVouchers;
//	@Column(name = "super_ticket_amount")
	private Long superTicketAmount;
//	@Column(name = "device_id")
	private String deviceId;
//	@Column(name = "latitude")
	private String latitude;
//	@Column(name = "longitude")
	private String longitude;
//	@Column(name = "distance")
	private String distance;
}
