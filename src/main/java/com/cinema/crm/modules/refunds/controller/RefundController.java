package com.cinema.crm.modules.refunds.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("initiate-refund")
	public ResponseEntity<Object> initiateRefund(@RequestBody SingleRefundReq singleRefundReq, HttpServletRequest servletRequest){
		return refundService.initiateRefund(singleRefundReq);
	}
	
	@GetMapping("testGiftCard")
	public ResponseEntity<Object> testGiftCard(){
		return refundService.generateGiftCardToken();
	}

	@PostMapping("approve-refund")
	public ResponseEntity<Object> aproval(@RequestBody SingleRefundReq singleRefundReq, HttpServletRequest servletRequest){
		return refundService.aproval(singleRefundReq);
	}
}
