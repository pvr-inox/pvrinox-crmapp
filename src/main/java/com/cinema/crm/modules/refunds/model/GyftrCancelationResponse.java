package com.cinema.crm.modules.refunds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GyftrCancelationResponse {

	private String ErrorCode;
	private String ErrorMsg;
	private String FailedVoucherNumber;
	private String Message;
	private String ProductCode;
	private String ProductName;
	private String ResultType;
	private String Authorizationcode;
	private String VoucherNumber;
	private String VoucherType;
	private String LastConsumedShopCode;
	private String LastConsumedDate;
	private String Value;
	private String Status;
	private String DurationEndDate;
	private String DurationStartDate;

}
