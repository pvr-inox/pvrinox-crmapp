package com.cinema.crm.databases.pvrinoxcrm.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show_transactions")
public class ShowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String uniqueRequestId;
    private String bookingId;
    private String rrn;
    private String payauthId;
    private String pgId;
    private String PGAmount;
    private String PGTransactionId;
    private String PGAuthId;

    private String secPgId;

    private String secPgAmount;

    private String secPgAuthId;

    private String secPgTransactionId;

    private String bookingStatus;
    private String bookingDateTime;
    private String mobile;
    private String username;
    private String theaterName;
    private String theaterId;
    private String audi;
    private String seats;
    private String showClass;
    private String showClassName;
    private int fnbCount;
    private int fnbTotalPrice;
    private int balanceAmount;
    private String conv;
    private String discount;
    private String donation;
    private String donationType;
    private int numOfSeats;
    private int passportTicketValue;
    private String ticketPrice;
    private String totalTicketPrice;
    private String partner;
    private String paymentStatus;
    private String paymode;
    private String couponType;
    private boolean isRefunded;
    private boolean isCancelled;
    private int cancelFee;
    private int refundedAmount;
    private String sapCode;
    private String revisedTicketAmount;
    private boolean flexiApplicable;
    private String voucherId;
    private String voucherCode;
    private LocalDateTime redeemDate;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Show show;
}
