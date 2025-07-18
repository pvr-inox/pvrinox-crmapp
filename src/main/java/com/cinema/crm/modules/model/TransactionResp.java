package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResp {
	
	public String bookingId;
    public String eventName;
    public String orderId;
    public String mobile;
    public String seatNumber;
    public String paymodes;
    public String voucherCode;
    public String voucherStatus;
    public String cancelReasons;
    public String cancelDate;
    public boolean isRefunded;
    public double totalAmount;
    public String refundStatus;
    public String utrNumber;
    public String customerName;
    public String paymentStatus;
    public String bookingStatus;
    public String showEventDateTime;
    public String cinemaName;
    public String city;
    public String submittedDate;
    public String nodalOfficer; // status from nodal officer
    public String rrnNumber;
}
