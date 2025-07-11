package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCancelData {
    private boolean success;
    private int statusId;
    private String errorMsg;
    private String cinemaId;
    private String chain;
    private String pos;
    private long canChargePercent;
    private long basicCanCharge;
    private long canChgCGST;
    private long canChgSGST;
    private long canChgUTGST;
    private long canChgOtherTax;
    private long totalCanCharge;
    private long refundAmount;
    private long ticketRefundPercent;
    private List<SBTickets> tickets = new ArrayList<>();

    @Getter
    @Setter
    public static class SBTickets {
        private String ticketNo;
        private String rowNo;
        private String seatNo;
        private String rate;
        private String gstCNNo;
        private String ticketPrice;
        private String cgst;
        private String sgst;
        private String utgst;
        private String rebate;
    }
}