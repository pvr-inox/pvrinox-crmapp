package com.cinema.crm.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespJuspayUPI {
	
	private String payer_app = "";
	private String payer_vpa = "";
	private String txn_flow_type = "";

}
