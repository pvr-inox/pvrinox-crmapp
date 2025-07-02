package com.cinema.crm.databases.pvrinox.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "showBiz_transactions")
@NoArgsConstructor
public class SBTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 20)
    private String platform;
    @Column(length = 20)
    private String chain;
    private int theaterId;
    @Column(length = 20)
    private String cinemaCode;
    private long requestId;
    private String bookingId;
    @Column(length = 20)
    private String requestType;
    @Column(columnDefinition = "LONGTEXT")
    private String request;
    private Date requestTime;
    @Column(columnDefinition = "LONGTEXT")
    private String response;
    private Date responseTime;
    private int timeTaken;

    public SBTransactions(String platform, String chain, String cinemaCode, String requestType, String request,
                          Date requestTime, long requestId, String bookingId) {
        this.platform = platform;
        this.chain = chain;
        this.cinemaCode = cinemaCode;
        this.requestType = requestType;
        this.request = request;
        this.requestTime = requestTime;
        this.requestId = requestId;
        this.bookingId = bookingId;
    }
}
