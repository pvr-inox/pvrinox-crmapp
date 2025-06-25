package com.cinema.crm.modules.refunds.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.refunds.service.RefundService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/refund/")
public class RefundController {

	private final RefundService refundService;
	
	public RefundController(RefundService refundService) {
		this.refundService = refundService;
	}

	@PostMapping("signleRefund")
	public ResponseEntity<Object> signleRefund(@RequestBody SingleRefundReq singleRefundReq, HttpServletRequest servletRequest){
		return refundService.signleRefund(singleRefundReq);
	}

}
