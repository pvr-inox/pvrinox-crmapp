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
    public String vouchers;
    public String voucherStatus;
    public String cancelReasons;
    public String cancelDate;
    public boolean refunded;
    public double totalAmount;
    public String approval;
    public String refundStatus;
    public String utrNumber;
    public String customerName;

}
