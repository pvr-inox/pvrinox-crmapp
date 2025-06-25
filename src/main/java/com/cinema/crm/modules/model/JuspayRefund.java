package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuspayRefund {
	private String unique_request_id;
	private String status;
	private boolean sent_to_gateway;
	private String refund_type;
	private String refund_source;
	private String ref;
	private String initiated_by;
	private String id;
	private String error_message;
	private String error_code;
	private String created;
	private double amount;
	private String arn = "";

}
