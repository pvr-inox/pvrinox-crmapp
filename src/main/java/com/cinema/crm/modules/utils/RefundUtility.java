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
 
    @Value("${timeout.juspay}") private long JUSPAY_TIMEOUT;
    
    private final HttpUtil httpUtil;
    
    public RefundUtility(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}

	private Map<String, String> getHeaders() {
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
        	 String response = httpUtil.invoke(JUSPAY_API_URL + "orders/" + bookingid, getHeaders(), String.valueOf(bookingid), JUSPAY_TIMEOUT);
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
}
