package com.cinema.crm.modules.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.crm.modules.model.TransactionReq;
import com.cinema.crm.modules.service.TransactionService;

@RestController
@RequestMapping("/api/v1/manage/")
public class TransactionContoller {
	
	private final TransactionService transactionService;
	
	public TransactionContoller(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(value  = "transactions")
	public ResponseEntity<Object> getTtrans(@RequestBody TransactionReq transactionReq){
		return transactionService.getAllTransactions(transactionReq);
	}
	
	@GetMapping(value  = "sessionData")
	public ResponseEntity<Object> getSessionTtrans(@RequestParam long sessionId){
		return transactionService.getSessionTtrans(sessionId);
	}
	
	@GetMapping(value  = "cancelledtxn")
	public ResponseEntity<Object> getCancellableRefunds() {
	    return transactionService.getCancellableRefunds();
	}

}


