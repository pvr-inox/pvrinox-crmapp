package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SingleRefundRequest {
	
	public String bookingId;
    public String eventName;
    public String customerName;
    public String refundType;
    public String refundReasons;
    public String totalAmount;
    public String refundAmount;
    public String refund;
    public String paymentGateway;
    public String remarks;
    public String action;

}
