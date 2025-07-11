package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCommitData {
    private boolean success;
    private int statusId;
    private String errorMsg;
    private String cancelStatus;
    private String cinemaId;
    private String chain;
    private String pos;
    private int orderId;
    private String orderIdEx;
    private String bookingId;
    private String itemBookingId;
    private String gstInvoiceNumber;
    private String auditNumber;
    private boolean cvp = false;
    private List<SBIssuedVoucher> vouchers = new ArrayList<>();
    private List<SBTickets> tickets = new ArrayList<>();

    private long canChargePercent;
    private long basicCanCharge;
    private long canChgCGST;
    private long canChgSGST;
    private long canChgUTGST;
    private long canChgOtherTax;
    private long totalCanCharge;
    private long refundAmount;
    private long ticketRefundPercent;
    private String unpaidToPaidPaySource;

    @Getter
    @Setter
    public static class SBIssuedVoucher {
        private long vouId;
        private String vouNo;
        private String validFrom;
        private String validTo;
    }


    @Getter
    @Setter
    public static class SBTickets {
        private String ticketNo;
        private String rowNo;
        private String seatNo;
        private String rate;
        private String gstInvoiceNo;
        private String ticketPrice;
        private String cGST;
        private String sGST;
        private String gstCNNo;
    }
}