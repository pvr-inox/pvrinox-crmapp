package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqFnMarkRefund {
	
	private String ccode;
	private String bookId;
	private String platform;
    private String chain;
	private String bkConfNo;
	private String uniqueRequestId;
	private String refundUniqueRequestId;
	private String referenceNo;

}
