package com.cinema.crm.modules.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GatewayResponse{
    @JsonProperty("STATUS") 
    private String status;
    @JsonProperty("TXNID") 
    private String txnId;
    @JsonProperty("CHECKSUMHASH") 
    private String checksumHash;
    @JsonProperty("SUBS_ID") 
    private String subs_id;
    @JsonProperty("RESPMSG") 
    private String respMsg;
    @JsonProperty("TXNDATETIME") 
    private String txnDateTime;
    @JsonProperty("TXNAMOUNT") 
    private String txnAmount;
    @JsonProperty("PAYMENTMODE") 
    private String paymentMode;
    @JsonProperty("BANKTXNID") 
    private String bankTxnId;
    @JsonProperty("CUSTID") 
    private String custId;
    @JsonProperty("CURRENCY") 
    private String currency;
    @JsonProperty("GATEWAYNAME") 
    private String gatewayName;
    @JsonProperty("MERC_UNQ_REF") 
    private String merc_unq_ref;
    @JsonProperty("MID") 
    private String mid;
    @JsonProperty("ORDERID") 
    private String orderId;
    @JsonProperty("TXNDATE") 
    private String txnDate;
    @JsonProperty("RESPCODE") 
    private String respCode;
    @JsonProperty("BANKNAME") 
    private String bankName;
}
