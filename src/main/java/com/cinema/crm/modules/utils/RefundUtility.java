package com.cinema.crm.modules.utils;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cinema.crm.modules.model.JuspayOrderStatus;
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
    
    public RefundUtility(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}

	private Map<String, String> getJuspayHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Basic " + JUSPAY_API_KEY);
        headers.put("x-merchantid", JUSPAY_MID);
        headers.put("version", "2021-10-25");
        return headers;
    }
    
    public JuspayOrderStatus juspayOrderStatus(String bookingid) {
        JuspayOrderStatus orderStatusVO = null;
        try {
        	 String response = httpUtil.invoke(JUSPAY_API_URL + "orders/" + bookingid, getJuspayHeaders(), String.valueOf(bookingid), JUSPAY_TIMEOUT);
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
            payload.put("DateAtClient", "2025-06-25 15:30:00");

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
    
    public Object cancelGiftCard() {
    	try {
    	
    	
    	} catch (Exception e) {
            log.error("exception occured in cancelling gift card: ", e);
        }
    	return null;
    }

}
