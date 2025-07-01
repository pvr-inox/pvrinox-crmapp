//package com.cinema.crm.modules.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "show_transactions")
//public class ShowTransaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String orderId;
//    private String uniqueRequestId;
//    private String bookingId;
//    private String rrn;
//    private String payauthId;
//    private String pgId;
//    private String PGAmount;
//    private String PGTransactionId;
//    private String PGAuthId;
//
//    @Column(name = "sec_pg_id")
//    private String secPGId;
//
//    @Column(name = "sec_pg_amount")
//    private String secPGAmount;
//
//    @Column(name = "sec_pg_auth_id")
//    private String secPGAuthId;
//
//    @Column(name = "sec_pg_transaction_id")
//    private String secPGTransactionId;
//
//    private String bookingStatus;
//    private String bookingDateTime;
//    private String mobile;
//    private String username;
//    private String theaterName;
//    private String theaterId;
//    private String audi;
//    private String seats;
//    private String showClass;
//    private String showClassName;
//    private int fnbCount;
//    private int fnbTotalPrice;
//    private int balanceAmount;
//    private String conv;
//    private String discount;
//    private String donation;
//    private String donationType;
//    private int numOfSeats;
//    private int passportTicketValue;
//    private String ticketPrice;
//    private String totalTicketPrice;
//    private String partner;
//    private String paymentStatus;
//    private String paymode;
//    private String couponType;
//    private boolean isRefunded;
//    private boolean isCancelled;
//    private int cancelFee;
//    private int refundedAmount;
//    private String sapCode;
//    private String revisedTicketAmount;
//    private boolean flexiApplicable;
//    private String voucherId;
//    private String voucherCode;
//    private LocalDateTime redeemDate;
//
//    @ManyToOne
//    @JoinColumn(name = "movie_id")
//    private Show show;
//}
