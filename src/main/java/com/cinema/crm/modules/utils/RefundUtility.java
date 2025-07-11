package com.cinema.crm.modules.utils;


import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.cinema.crm.config.RefundPropertiesConfiguration;
import com.cinema.crm.databases.pvrinox.entities.Configuration;
import com.cinema.crm.databases.pvrinox.entities.GiftcardRedeemDetail;
import com.cinema.crm.databases.pvrinox.repositories.ConfigurationRepository;
import com.cinema.crm.modules.model.JuspayOrderStatus;
import com.cinema.crm.modules.refunds.model.GyftrCancelationResponse;
import com.cinema.crm.modules.refunds.model.JuspayRefundResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RefundUtility {

    private final HttpUtil httpUtil;
    private final ConfigurationRepository configurationRepository;
    private final RefundPropertiesConfiguration refundPros;
    
    public RefundUtility(HttpUtil httpUtil,ConfigurationRepository configurationRepository,RefundPropertiesConfiguration refundPros) {
		this.httpUtil = httpUtil;
		this.configurationRepository = configurationRepository;
		this.refundPros = refundPros;
	}

	private Map<String, String> juspayGetHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Basic " + refundPros.getJuspayKey());
        headers.put("x-merchantid", refundPros.getJuspayMid());
        headers.put("version", "2021-10-25");
        return headers;
    }
	
    private Map<String, String> juspayPostHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Basic " + refundPros.getJuspayKey());
        headers.put("Content-Type", "application/json");
        headers.put("x-merchantid", refundPros.getJuspayMid());
        headers.put("version", "2021-10-25");

        return headers;
    }
    
    public JuspayOrderStatus juspayOrderStatus(String bookingid) {
        JuspayOrderStatus juspayOrderStatus = JuspayOrderStatus.builder().build();
        try {
        	 String response = httpUtil.invoke(refundPros.getJuspayUrl() + "orders/" + bookingid, juspayGetHeaders(), String.valueOf(bookingid), refundPros.getTimeout());
            log.debug("juspay order status response for booking id : {} : {}", bookingid, response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            juspayOrderStatus = mapper.readValue(response, JuspayOrderStatus.class);
            log.debug("juspay order status response after mapping for booking id : {} : {}", bookingid, juspayOrderStatus);
            return juspayOrderStatus;
        } catch (Exception exception) {
        	log.error("exception occured in juspay order status for booking id : {} : {}", bookingid, exception.getStackTrace());
        }
        return juspayOrderStatus;
    }
    
    private Map<String, String> getGiftCardHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
    
    public Map<String, Object> generateGiftCardToken() {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("terminalId", refundPros.getGiftcardTerminalid());
            payload.put("userName", refundPros.getGiftcardUsername());
            payload.put("password", refundPros.getGiftcardPassword());
            payload.put("transactionId", 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            payload.put("DateAtClient", now.format(formatter));

            String response = httpUtil.invokePost(refundPros.getGiftcardUrl() +"/authorize", getGiftCardHeaders(), new Gson().toJson(payload), "application/json", "", refundPros.getTimeout());
            log.debug("Gift card token response : {}", response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Map<String, Object> responseMap = mapper.readValue(response, Map.class); 

            return responseMap;
        } catch (Exception exception) {
            log.error("exception occured in generating gift card token: ", exception.getMessage());
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

			String response = httpUtil.invokePost(refundPros.getGiftcardUrl() + "/gc/cancelredeem", headers, new Gson().toJson(payload), "application/json", "", refundPros.getTimeout());

			log.debug("Gift card cancelRedeem response : {}", response);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Map<String, Object> responseMap = mapper.readValue(response, Map.class);

			return responseMap;
		} catch (Exception exception) {
			log.error("exception occurred in cancelling gift card: ", exception.getMessage());
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
        JuspayRefundResponse refundResponse = JuspayRefundResponse.builder().build();
        try {
            String response = httpUtil.invokePost(refundPros.getJuspayUrl() + "orders/" + bookingid + "/refunds", juspayPostHeaders(), (new Gson()).toJson(request), "application/json", bookingid, refundPros.getTimeout());
            log.debug("juspay order refund response for booking id : {} : {}", bookingid, response);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            refundResponse = mapper.readValue(response, JuspayRefundResponse.class);
            log.debug("juspay order refund response after mapping for booking id : {} : {}", bookingid, new Gson().toJson(refundResponse));
            return refundResponse;
        } catch (Exception exception) {
        	log.error("exception occured in juspay order resfund for booking id : {} : {}", bookingid, exception.getMessage());
        }
        return refundResponse;
    }

	public GyftrCancelationResponse query(String bookingid, String promocode) {
		GyftrCancelationResponse cancelResponse = GyftrCancelationResponse.builder().build();
		log.error("refundPros:{}",refundPros);
		try {
			Map<String, String> request = new LinkedHashMap<String, String>();
			request.put("deviceCode", "P");
			request.put("merchantUid", refundPros.getGyftrUserid());
			request.put("ShopCode", refundPros.getGyftrShopcode());
			request.put("voucherNumber", promocode);
			request.put("Password", URLEncoder.encode(refundPros.getGyftrPassword(), "UTF-8"));
			request.put("requestJobNumber", bookingid);
			log.debug("voucha consume request for booking id : {} : {}", bookingid, request);
			
			String response = httpUtil.invoke(refundPros.getGyftrUrl() + "/QueryVoucher?" + getJson(request), null, bookingid, refundPros.getTimeout());
			
			log.debug("voucha consume response for booking id : {} : {}", bookingid, response);
			JsonArray arr = JsonParser.parseString(response).getAsJsonObject().get("vQueryVoucherResult").getAsJsonArray();
			if (arr != null && arr.size() > 0) {
				cancelResponse = (new Gson()).fromJson(arr.get(0), GyftrCancelationResponse.class);
				log.debug("voucha consume response after mapping for booking id : {} : {}", bookingid, cancelResponse);
				return cancelResponse;
			} else {
				return null;
			}
		} catch (Exception exception) {
			log.error("exception occured in voucha consume for booking id : {} : {}", bookingid, exception.getMessage());
		}
		return cancelResponse;
	}

	private String getJson(Map<String, String> map) {
		StringBuilder postData = new StringBuilder();
		for (Entry<String, String> param : map.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}
			postData.append(param.getKey());
			postData.append('=');
			postData.append(param.getValue());
		}
		return postData.toString();
	}

	public GyftrCancelationResponse cancel(String bookingid, String promocode) {
		GyftrCancelationResponse gyftrCancelResponse = GyftrCancelationResponse.builder().build();
		try {
			Map<String, String> request = new LinkedHashMap<String, String>();
			request.put("deviceCode", "P");
			request.put("merchantUid", refundPros.getGyftrUserid());
			request.put("ShopCode", refundPros.getGyftrShopcode());
			request.put("voucherNumber", promocode);
			request.put("Password", URLEncoder.encode(refundPros.getGyftrPassword(), "UTF-8"));
			request.put("requestJobNumber", bookingid);
			log.debug("voucha consume request for booking id : {} : {}", bookingid, request);
			
			String response = httpUtil.invoke(refundPros.getGyftrUrl() + "/Cancel?" + getJson(request), null, bookingid, refundPros.getTimeout());
			
			log.debug("voucha consume response for booking id : {} : {}", bookingid, response);
			JsonArray arr = JsonParser.parseString(response).getAsJsonObject().get("vCancelResult").getAsJsonArray();
			if (arr != null && arr.size() > 0) {
				gyftrCancelResponse = (new Gson()).fromJson(arr.get(0), GyftrCancelationResponse.class);
				log.debug("voucha consume response after mapping for booking id : {} : {}", bookingid, gyftrCancelResponse);
				return gyftrCancelResponse;
			} else {
				return null;
			}
		} catch (Exception exception) {
			log.error("exception occured in voucha consume for booking id : {} : {}", bookingid, exception.getMessage());
		}
		return gyftrCancelResponse;
	}
	
	public Map<String, Object> cancelBooking(String bookingId) {
		try {
			Map<String, String> request = new LinkedHashMap<String, String>();
			request.put("orderId", bookingId);
			Map<String, String> header = new LinkedHashMap<String, String>();
			header.put("Content-Type", "application/json");
			header.put("chain", "PVR");
			header.put("city", "Mumbai-All");
			header.put("country", "INDIA");
			header.put("platform", "MSITE");
			header.put("appVersion", "1.0");
			header.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzcwIiwiaWF0IjoxNzUxMjYzMzA0LCJleHAiOjE3NTE0NzkzMDR9.jiOkfZyiQ5Ggszixl0e_hlXmxpQxp86eljbp-vCETIo");
			String response = httpUtil.invokePost("https://pvrinox-maindev.pvrcinemas.com/" + "api/v1/booking/ticketing/cancelbooking",
					header, (new Gson()).toJson(request), "application/json", bookingId,
					refundPros.getTimeout());
			log.debug("juspay order refund response for booking id : {} : {}", bookingId, response);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Map<String, Object> responseMap = mapper.readValue(response, Map.class);
			return responseMap;
		} catch (Exception exception) {
			log.error("exception occured in voucha consume for booking id : {} : {}", bookingId,
					exception.getMessage());
		}

		return null;
	}
}
