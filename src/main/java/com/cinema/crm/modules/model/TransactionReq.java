package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionReq {

	public String mobile;
	public String bookingId;
	public String cinema;
	public String orderId;
	public String fromDate;
	public String uptoDate;
	public String transType;
	public boolean nodalOfficer;

}
