package com.cinema.crm.modules.refunds.model;

import java.util.List;

import com.cinema.crm.modules.model.JuspayCard;
import com.cinema.crm.modules.model.JuspayPaymentGatewayResponse;
import com.cinema.crm.modules.model.JuspayPaymentLink;
import com.cinema.crm.modules.model.JuspayRefund;
import com.cinema.crm.modules.model.JuspayTxnDetail;
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
public class JuspayRefundResponse {

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

	private String txn_uuid;
	private String txn_id;
	private int status_id;
	private String status;
	private String rewards_breakup;
	private String return_url;

	private JuspayTxnDetail txn_detail;
	List<JuspayRefund> refunds;

	private boolean refunded;
	private String product_id;
	private String payment_method_type;
	private String payment_method;

	private JuspayPaymentLink payment_links;
	private JuspayPaymentGatewayResponse payment_gateway_response;

	private String order_id;
	private String merchant_id;
	private String id;
	private String gateway_reference_id;
	private int gateway_id;
	private int effective_amount;
	private String date_created;
	private String customer_phone;
	private String customer_id;
	private String customer_email;
	private String currency;

	private JuspayCard card;
	private String bank_pg;
	private String bank_error_message;
	private String bank_error_code;
	private String auth_type;
	private int amount_refunded;
	private int amount;
	private String error_code;
	private String error_message;

}
