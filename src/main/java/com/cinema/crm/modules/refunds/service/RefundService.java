package com.cinema.crm.modules.refunds.service;

import org.springframework.http.ResponseEntity;

import com.cinema.crm.modules.model.SingleRefundReq;

public interface RefundService {

	ResponseEntity<Object> initiateRefund(SingleRefundReq singleRefundReq);
}
