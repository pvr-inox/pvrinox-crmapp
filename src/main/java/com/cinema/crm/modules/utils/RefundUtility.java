package com.cinema.crm.modules.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cinema.crm.modules.database.main.GiftcardRedeemDetail;
import com.cinema.crm.modules.entity.Configuration;
import com.cinema.crm.modules.entity.ConfigurationRepository;
import com.cinema.crm.modules.model.JuspayOrderStatus;
import com.cinema.crm.modules.refunds.model.JuspayRefundResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RefundUtility {

    @Value("${juspay.mid}")
    private String JUSPAY_MID;
    @Value("${juspay.client_id}")
    private String JUSPAY_CLIENT_ID;
    @Value("${juspay.url}")
    private String JUSPAY_API_URL;
    @Value("${juspay.key}")
    private String JUSPAY_API_KEY;
    @Value("${juspay.username}")
    private String JUSPAY_USERNAME;
    @Value("${juspay.password}")
    private String JUSPAY_PASSWORD;
    @Value("${giftcard.url}")
    private String GIFTCARD_API_URL;
    @Value("${giftcard.terminalid}")
    private String GIFTCARD_TERMINAL_ID;
    @Value("${giftcard.username}")
    private String GIFTCARD_USERNAME;
    @Value("${giftcard.password}")
    private String GIFTCARD_PASSWORD;
    @Value("${giftcard.transactionid}")
    private int GIFTCARD_TRANSACTION_ID;

 
    @Value("${timeout.juspay}") private long JUSPAY_TIMEOUT;
    
    private final HttpUtil httpUtil;
    private final ConfigurationRepository configurationRepository;
    
    public RefundUtility(HttpUtil httpUtil,ConfigurationRepository configurationRepository) {
		this.httpUtil = httpUtil;
		this.configurationRepository = configurationRepository;
	}

	private Map<String, String> juspayGetHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Basic " + JUSPAY_API_KEY);
        headers.put("x-merchantid", JUSPAY_MID);
        headers.put("version", "2021-10-25");
        return headers;
    }
	
    private Map<String, String> juspayPostHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Basic " + JUSPAY_API_KEY);
        headers.put("Content-Type", "application/json");
        headers.put("x-merchantid", JUSPAY_MID);
        headers.put("version", "2021-10-25");

        return headers;
    }
    
    public JuspayOrderStatus juspayOrderStatus(String bookingid) {
        JuspayOrderStatus orderStatusVO = null;
        try {
        	 String response = httpUtil.invoke(JUSPAY_API_URL + "orders/" + bookingid, juspayGetHeaders(), String.valueOf(bookingid), JUSPAY_TIMEOUT);
            log.debug("JUSPAY ORDER STATUS RESPONSE FOR BOOKING ID :: {} :: {}", bookingid, response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            orderStatusVO = mapper.readValue(response, JuspayOrderStatus.class);
            log.debug("juspay order status response after mapping for booking id :: {} :: {}", bookingid,
                    new Gson().toJson(orderStatusVO));
            return orderStatusVO;
        } catch (Exception e) {
        	log.error("exception occured in juspay order status for booking id :: {} :: {}", bookingid,e.getStackTrace());
        }
        return orderStatusVO;
    }
    
    private Map<String, String> getGiftCardHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
    
    public Map<String, Object> generateGiftCardToken() {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("terminalId", GIFTCARD_TERMINAL_ID);
            payload.put("userName", GIFTCARD_USERNAME);
            payload.put("password", GIFTCARD_PASSWORD);
            payload.put("transactionId", 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            payload.put("DateAtClient", now.format(formatter));

            String response = httpUtil.invokePost(GIFTCARD_API_URL +"/authorize", getGiftCardHeaders(), new Gson().toJson(payload), "application/json", "", JUSPAY_TIMEOUT);
            log.debug("Gift card token response :: {}", response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Map<String, Object> responseMap = mapper.readValue(response, Map.class); 

            return responseMap;
        } catch (Exception e) {
            log.error("exception occured in generating gift card token: ", e);
        }
        return null;
    }
    
	public Map<String, Object> cancelGiftCard(GiftcardRedeemDetail giftcardRedeemDetail) {
		try {
			Map<String, Object> payload = new HashMap<>();
			payload.put("TransactionId", giftcardRedeemDetail.getTransactionId());
			payload.put("CardNumber", giftcardRedeemDetail.getCardNumber());
			payload.put("OriginalAmount", giftcardRedeemDetail.getAmount() / 100);
			payload.put("InvoiceNumber", giftcardRedeemDetail.getInvoiceNumber());
			payload.put("OriginalTransactionId", giftcardRedeemDetail.getTransactionId());
			payload.put("OriginalBatchNumber", giftcardRedeemDetail.getBatchNumber());
			payload.put("OriginalApprovalCode", giftcardRedeemDetail.getApprovalCode());
			payload.put("Notes", "cancel txn");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime now = LocalDateTime.now();
			payload.put("DateAtClient", now.format(formatter));
			payload.put("Idempotencykey", giftcardRedeemDetail.getTransactionId());
			
			Configuration value = configurationRepository.findByName("GIFT_CARD_TOKEN");
			
			 Map<String, String> headers = new HashMap<String, String>();
		        headers.put("Content-Type", "application/json");
		        headers.put("Authorization", "Bearer " + value.getValue());

			String url = GIFTCARD_API_URL + "/gc/cancelredeem";

			String response = httpUtil.invokePost(url, headers, new Gson().toJson(payload),
					"application/json", "", JUSPAY_TIMEOUT);

			log.debug("Gift card cancelRedeem response :: {}", response);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Map<String, Object> responseMap = mapper.readValue(response, Map.class);

			return responseMap;
		} catch (Exception e) {
			log.error("exception occurred in cancelling gift card: ", e);
		}
		return null;
	}

    public JuspayRefundResponse juspayOrderRefund(String bookingid, String amount) {
        Map<String, String> request = new LinkedHashMap<String, String>();
        Random random = new Random();
        // Generate a random number between 10 and 99 (inclusive)
        int randomNumber = random.nextInt(90) + 10;
        request.put("unique_request_id", "REF-" + randomNumber + "-" + bookingid);
        request.put("amount", amount);
        JuspayRefundResponse refundResponseVO = null;
        try {
            String response = httpUtil.invokePost(JUSPAY_API_URL + "orders/" + bookingid + "/refunds", juspayPostHeaders(), (new Gson()).toJson(request), "application/json", bookingid, JUSPAY_TIMEOUT);
            log.debug("JUSPAY ORDER REFUND RESPONSE FOR BOOKING ID :: {} :: {}", bookingid, response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            refundResponseVO = mapper.readValue(response, JuspayRefundResponse.class);
            log.debug("JUSPAY ORDER REFUND RESPONSE AFTER MAPPING FOR BOOKING ID :: {} :: {}", bookingid, new Gson().toJson(refundResponseVO));
            return refundResponseVO;
        } catch (Exception e) {
        	log.error("EXCEPTION OCCURED IN JUSPAY ORDER RESFUND FOR BOOKING ID :: {} :: {}", bookingid, e.getMessage());
        }
        return refundResponseVO;
    }

}
