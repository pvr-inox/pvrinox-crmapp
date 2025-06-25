package com.cinema.crm.modules.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuspayPaymentGatewayResponse {

	private String created;
	private String epg_txn_id;
	private String rrn;
	private String auth_id_code;
	private String txn_id;
	private String resp_code;
	private String resp_message;
	private GatewayResponse gateway_response;

}

