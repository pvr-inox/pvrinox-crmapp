package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SingleRefundReq {
	
	public String bookingId;
    public String eventName;
    public String customerName;
    public String refundType;
    public String refundReasons;
    public double totalAmount;
    public double refundAmount;
    public double refund;
    public String paymentGateway;
    public String remarks;

}
