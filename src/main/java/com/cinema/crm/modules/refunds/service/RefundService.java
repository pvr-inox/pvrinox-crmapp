package com.cinema.crm.modules.refunds.service;

import org.springframework.http.ResponseEntity;

import com.cinema.crm.modules.model.SingleRefundRequest;

public interface RefundService {

	ResponseEntity<Object> initiateRefund(SingleRefundRequest singleRefundReq);

	ResponseEntity<Object> aproval(SingleRefundRequest singleRefundReq);
	
	//ResponseEntity<Object> cancelTrans(String id);
}
