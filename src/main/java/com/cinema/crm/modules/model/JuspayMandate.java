package com.cinema.crm.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class JuspayMandate {

	private boolean is_handle_supported;
	private String mandate_token;
	private String mandate_status;
	private String mandate_id;
	private String mandate_type;
	private long start_date;
	private long end_date;
	private boolean revokable_by_customer;
	private double max_amount;
	private String frequency;
	private String amount_rule;
}
