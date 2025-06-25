package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuspayTxnDetail {

	private String txn_uuid;
	private String txn_id;
	private double txn_amount;
	private String tax_amount;
	private String surcharge_amount;
	private String status;
	private boolean redirect;
	private String order_id;
	private double net_amount;
	private int gateway_id;
	private String gateway;
	private boolean express_checkout;
	private String error_message;
	private String error_code;
	private String currency;
	private String created;

}

