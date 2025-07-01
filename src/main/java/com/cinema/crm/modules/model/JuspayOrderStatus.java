package com.cinema.crm.modules.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuspayOrderStatus {

	private String id;
	private String merchant_id;
	private String order_id;
	private String customer_id;
	private String customer_email;
	private String customer_phone;
	private String product_id;
	private String date_created;

	private String status;
	private int status_id;
	private double amount;
	private String currency;

	private Boolean refunded;
	private Long amount_refunded;
	private String return_url;

	private String txn_id;
	private String txn_uuid;
	private String auth_type;
	private int gateway_id;

	private String resp_code;
	private String resp_message;

	private String bank_error_code;
	private String bank_error_message;

	private String payment_method_type = "";
	private String payment_method = "";
	private String payer_app_name = "";
	private String payer_vpa = "";
	
	private RespJuspayUPI upi;
	private JuspayCard card;
	private JuspayPaymentGatewayResponse payment_gateway_response;
	private JuspayPaymentLink payment_links;
	private JuspayTxnDetail txn_detail;
	private JuspayMandate mandate;
	List<JuspayRefund> refunds;

	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String udf9;
	private String udf10;

	private String error_code;
	private String error_message;

}
