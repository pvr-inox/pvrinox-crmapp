package com.cinema.crm.modules.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.service.TransactionService;

@RestController
@RequestMapping("/api/v1/refund/")
public class RefundController {

	private final TransactionService transactionService;

	public RefundController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping(value = "signleRefund")
	public ResponseEntity<Object> signleRefund(SingleRefundReq singleRefundReq){
		return transactionService.signleRefund(singleRefundReq);
	}

}
