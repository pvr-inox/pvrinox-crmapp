package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionReq {

	public String mobile;
	public String bookingId;
	public boolean requested;

}
