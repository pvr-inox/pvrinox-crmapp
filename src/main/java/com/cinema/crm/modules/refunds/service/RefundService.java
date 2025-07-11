package com.cinema.crm.modules.refunds.service;

import org.springframework.http.ResponseEntity;

import com.cinema.crm.modules.model.CancelSessionRequest;
import com.cinema.crm.modules.model.SingleRefundRequest;

public interface RefundService {

	ResponseEntity<Object> initiateRefund(SingleRefundRequest singleRefundReq);

	ResponseEntity<Object> aproval(SingleRefundRequest singleRefundReq);
	
//	ResponseEntity<Object> cancelAllTransactionsBySessionId(Long sessionId);
	
	ResponseEntity<Object> cancelTransactionsBySession(CancelSessionRequest request);

	
	//ResponseEntity<Object> cancelTrans(String id);
}
