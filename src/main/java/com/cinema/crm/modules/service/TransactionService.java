package com.cinema.crm.modules.service;

import org.springframework.http.ResponseEntity;

import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.model.TransactionReq;

public interface TransactionService {
	
	ResponseEntity<Object> getAllTransactions(TransactionReq transactionReq);
	
	ResponseEntity<Object> getSessionTtrans(long sessionId);
	
	ResponseEntity<Object> signleRefund(SingleRefundReq singleRefundReq);
	
}
