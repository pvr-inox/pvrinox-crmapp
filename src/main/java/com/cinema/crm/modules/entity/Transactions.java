package com.cinema.crm.modules.entity;

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
@Table(name = "transactions")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    private String id;

    private String bookType;
    private String bookingId;
    private String bookingStatus;
    private LocalDateTime bookingTime;
    private LocalDate businessDate;
    private LocalDateTime cancelTime;
    private String chain;
    private LocalDateTime createdAt;
    private String dtmSource;
    private String email;
    private String foodRefId;
    private String ipAddress;
    private String mobile;
    private LocalDateTime modifiedAt;
    private String name;
    private Boolean cancelAvail;
    private String certificate;
    private String cityName;
    private String filmId;
    private String filmMasterId;
    private String filmName;
    private String format;
    private String genre;
    private String language;
    private String stategstn;
    private String theaterId;
    private String theaterName;
    private Integer fbCount;
    private Long fbDiscount;
    private Long fbTotalPrice;
    private Boolean fnb;
    private String foodType;

    @Column(columnDefinition = "json")
    private String foods;

    private String pickUpTime;
    private Integer orderId;
    private Integer adultSeats;
    private String audi;
    private String balanceAmount;
    private Integer childSeats;
    private Long childTicketPrice;
    private Long conv;
    private Long convGst;
    private Long convTotal;
    private Boolean cvp;
    private Long cvpAmount;
    private String cvpInfo;
    private String cvpVouchers;
    private Long discount;
    private Long donation;
    private String donationType;
    private LocalDateTime endTime;
    private String experience;
    private Integer numOfSeats;
    private Long passportTicketValue;
    private String pgrpId;
    private Long rateCard;
    private Integer screenId;
    private String seats;
    private Long sessionId;
    private String showClass;
    private String showClassName;
    private LocalDateTime showTime;
    private Long ticketPrice;
    private Long totalTicketPrice;

    @Column(columnDefinition = "text")
    private String vistaException;

    private String vistaState;
    private LocalDateTime vistaTime;
    private String partnerId;
    private String paymentStatus;
    private String paymode;
    private String couponType;
    private Boolean couponused;
    private Boolean dccardused;
    private Boolean giftcardused;
    private Boolean gyfterused;
    private Boolean hyattused;
    private Boolean juspayused;
    private String pgType;
    private String platform;
    private String pos;
    private Boolean qr;
    private String transId;
    private Long userId;
    private String appVersion;
    private Long juspayamt;
    private String platformCancel;
    private Boolean razorpayused;
    private String hsnSacCode;
    private String gstInvoiceNo;
    private Long refundedAmount;
    private String myTicket;
    private Boolean additionalLangClip;
    private String gstInvoiceNocan;
    private String gstInvoiceNocn;
    private Long ticketTax;

    @Column(columnDefinition = "json")
    private String surcharge;

    private Long surchargeAmount;
    private String posHoCode;
    private String sapCode;

    @Column(columnDefinition = "text")
    private String orderData;

    private Long revisedTicketAmount;
    private Boolean instapayused;
    private String eticketUrl;
    private Long cancelFee;
    private String paymodeRefund;
    private String eventType;
    private String dtmSourcePartner;
    private String dtmCampaign;
    private Boolean flexiApplicable;
    private Boolean flexiApply;
    private Long flexiTicketPrice;
    private Long ticketsRefunded;

    @Column(columnDefinition = "json")
    private String ticketInvoiceData;

    private Long familyBundleCharge;
    private Boolean familyBundle;
    private Long familyBundleAmount;
    private String familyBundleInfo;
    private String refCode;
    private String showOrderId;
    private String source;
    private String userType;

    @Column(columnDefinition = "json")
    private String fnbDeliveryStatus;

    private String deliveryMode;
    private Boolean superTicket;
    private String superTicketVouchers;
    private Long superTicketAmount;
    private String deviceId;
    private String latitude;
    private String longitude;
    private String distance;
}
