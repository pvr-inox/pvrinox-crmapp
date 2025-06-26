package com.cinema.crm.modules.refunds.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.InitiateRefundResponse;
import com.cinema.crm.constants.Constants.Message;
import com.cinema.crm.constants.Constants.RespCode;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.modules.entity.Configuration;
import com.cinema.crm.modules.entity.ConfigurationRepository;
import com.cinema.crm.modules.entity.Email;
import com.cinema.crm.modules.entity.NotificationTemplate;
import com.cinema.crm.modules.entity.RefundDetails;
import com.cinema.crm.modules.entity.Transactions;
import com.cinema.crm.modules.model.JuspayOrderStatus;
import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.model.WSReturnObj;
import com.cinema.crm.modules.refunds.service.RefundService;
import com.cinema.crm.modules.repository.NotificationTemplateRepository;
import com.cinema.crm.modules.repository.RefundDetailsRepository;
import com.cinema.crm.modules.repository.TransactionsRepository;
import com.cinema.crm.modules.utils.EmailUtil;
import com.cinema.crm.modules.utils.RefundUtility;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RefundServiceImpl implements RefundService {

	private final TransactionsRepository transactionsRepository;
	private final RefundDetailsRepository refundDetailsRepository;
	private final RefundUtility refundUtility;
	private final EmailUtil emailUtil;
	private final NotificationTemplateRepository notificationTemplateRepository;
	private final ConfigurationRepository configurationRepository;
	
	public RefundServiceImpl(TransactionsRepository transactionsRepository,
			RefundDetailsRepository refundDetailsRepository,RefundUtility refundUtility,EmailUtil emailUtil,
			NotificationTemplateRepository notificationTemplateRepository,ConfigurationRepository configurationRepository) {
		this.transactionsRepository = transactionsRepository;
		this.refundDetailsRepository = refundDetailsRepository;
		this.refundUtility = refundUtility;
		this.emailUtil = emailUtil;
		this.notificationTemplateRepository = notificationTemplateRepository;
		this.configurationRepository = configurationRepository;
	}

	@Override
	public ResponseEntity<Object> initiateRefund(SingleRefundReq singleRefundReq) {
		Optional<Transactions> opsTransactions = transactionsRepository.findById(singleRefundReq.getBookingId());
		if (opsTransactions.isPresent()) {
			Transactions transactions = opsTransactions.get();
			if (Constants.REFUND_INITIATE.equals(transactions.getPaymentStatus())) {
				return ResponseEntity.ok(InitiateRefundResponse.builder()
						.bookingId(singleRefundReq.getBookingId())
						.result(Result.ERROR)
						.responseCode(RespCode.FAILED)
						.message(Message.ALREADY_REQUESTED_NODAL_OFFICER)
						.build());
			}
			
			if (Constants.ROLLEDBACK.equals(transactions.getPaymentStatus())) {
				return ResponseEntity.ok(InitiateRefundResponse.builder()
						.bookingId(singleRefundReq.getBookingId())
						.result(Result.SUCCESS)
						.responseCode(RespCode.SUCCESS)
						.message(Message.ALREADY_PROCESSED)
						.build());
			}
			
			RefundDetails refundDetails = RefundDetails.builder()
					.bookingId(singleRefundReq.getBookingId())
					.eventName(singleRefundReq.eventName)
					.customerName(singleRefundReq.getCustomerName())
					.refundType(singleRefundReq.getRefundType())
					.refundReasons(singleRefundReq.getRefundReasons())
					.totalAmount(singleRefundReq.getTotalAmount())
					.refundAmount(singleRefundReq.getRefundAmount())
					.refund(singleRefundReq.getRefund())
					.paymentGateway(singleRefundReq.getPaymentGateway())
					.remarks(singleRefundReq.getRemarks())
					.refundStatus(Constants.REFUND_INITIATE)
					.build();
			
			// TODO EMAIL SEND TO NODAL OFFICER
			// TODO CHECK THE ROLE OF THE USER EITHER CRM EXUCATIVE EITHER RGM, PROCESS THE REQUEST AS PER ROLE
			Optional<NotificationTemplate> opsNotificationTemplate = notificationTemplateRepository.findById("REFUND_APPROVAL");
			if (opsNotificationTemplate.isPresent()) {
				NotificationTemplate template = opsNotificationTemplate.get();

				String aprovalEmail = template.getEmailTemplate()
				    .replace("<NODAL_OFFICER_NAME>", "Donald Trump")
				    .replace("<BOOKING_ID>", singleRefundReq.getBookingId())
				    .replace("<REFUND_AMOUNT>", String.valueOf(singleRefundReq.getRefundAmount()))
				    .replace("<CUSTOMER_NAME>", transactions.getName().toUpperCase())
				    .replace("<MOBILE_NUMBER>", transactions.getMobile())
				    .replace("<REFUND_REASON>", singleRefundReq.getRemarks());

				
				Email email = new Email(aprovalEmail, template.getEmailSubject() + singleRefundReq.getBookingId(), template.getEmailFrom(),
						"akshay.chandekar@aurusit.com", template.getEmailCc(), template.getEmailBcc(), new Date(), 5, template.getName(),
						"", transactions.getChain(), false);
				
				emailUtil.send(email);
				refundDetailsRepository.save(refundDetails);
				transactions.setPaymentStatus(Constants.REFUND_INITIATE);
				transactionsRepository.save(transactions);
			}
			
			//TODO CHECK PAYMODES SEND REFUND REQUEST AS PER THE PAYMODE
//			this.juspay(transactions.getBookingId());
			
			return ResponseEntity.ok(InitiateRefundResponse.builder()
					.bookingId(singleRefundReq.getBookingId())
					.result(Result.SUCCESS)
					.responseCode(RespCode.SUCCESS)
					.message(Message.REQUESTED_NODAL_OFFICER)
					.build());
			
		}

		return ResponseEntity.ok(InitiateRefundResponse.builder()
				.bookingId(singleRefundReq.getBookingId())
				.result(Result.ERROR)
				.responseCode(RespCode.NO_DATA_FOUND)
				.message(Message.BOOKING_NOT_FOUNDED)
				.build());
	}

	private void juspay(String bookingId) {
		JuspayOrderStatus orderStatus = refundUtility.juspayOrderStatus(bookingId);
		
	}
	
	private Object cancelGiftCard(String transactionId, String cardNumber, String originalAmount,
			String invoiceNumber, String originalTransactionId, String originalBatchNumber, String originalApprovalCode,
			String idempotencyKey) {
		try {
			Map<String, Object> cancelResponse = refundUtility.cancelGiftCard(transactionId, cardNumber, originalAmount,
					invoiceNumber, originalTransactionId, originalBatchNumber, originalApprovalCode, idempotencyKey);
			if (Objects.nonNull(cancelResponse) && 0 == (int) cancelResponse.get("ResponseCode")
					&& "Transaction successful.".equalsIgnoreCase((String) cancelResponse.get("ResponseMessage"))) {
				log.info("cancel gift card success response: " ,new Gson().toJson(cancelResponse));
				
			}else{
				log.info("cancel gift card failed response: " ,new Gson().toJson(cancelResponse));
			}
			return cancelResponse;
			
		} catch (Exception e) {
			log.error("GIFT CARD ERROR: ", e);
		}
		
		return null;
	}
	
	@Override
	public ResponseEntity<Object> generateGiftCardToken() {
		String authToken = "";
		try {
			Map<String, Object> tokenResponse = refundUtility.generateGiftCardToken();
			log.info("Token response: " ,new Gson().toJson(tokenResponse));
			if(Objects.nonNull(tokenResponse) && 0 == (int) tokenResponse.get("responseCode") && "Transaction successful.".equalsIgnoreCase((String) tokenResponse.get("responseMessage"))){
				authToken = (String) tokenResponse.get("authToken");
				Configuration value = configurationRepository.findByName("GIFT_CARD_TOKEN");
				if(Objects.nonNull(value)) value.setValue(authToken);
				configurationRepository.save(value);
				
				cancelGiftCard("6950748", "6699960102004658", "1", "200250006950748679", "6950748", "14652027", "116272471", "1750856331874");
			}else {
				ResponseEntity.ok(null);
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			log.error("GIFT CARD ERROR: ", e);
		}
		return ResponseEntity.ok(null);
	}
	
	


}
