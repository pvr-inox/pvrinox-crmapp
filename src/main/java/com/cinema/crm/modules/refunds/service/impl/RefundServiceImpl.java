package com.cinema.crm.modules.refunds.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.Constants.Message;
import com.cinema.crm.constants.Constants.RespCode;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.modules.database.main.GiftcardRedeemDetail;
import com.cinema.crm.modules.entity.Configuration;
import com.cinema.crm.modules.entity.ConfigurationRepository;
import com.cinema.crm.constants.InitiateRefundResponse;
import com.cinema.crm.modules.entity.Email;
import com.cinema.crm.modules.entity.JuspayRedeemDetail;
import com.cinema.crm.modules.entity.NotificationTemplate;
import com.cinema.crm.modules.entity.RefundDetails;
import com.cinema.crm.modules.entity.Transactions;
import com.cinema.crm.modules.model.JuspayOrderStatus;
import com.cinema.crm.modules.model.JuspayRefund;
import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.refunds.model.JuspayRefundResponse;
import com.cinema.crm.modules.refunds.service.RefundService;
import com.cinema.crm.modules.repository.JuspayRedeemDetailRepository;
import com.cinema.crm.modules.repository.GiftcardRedeemDetailRepository;
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
	private final GiftcardRedeemDetailRepository giftcardRedeemDetailRepository;
	private final JuspayRedeemDetailRepository juspayRedeemDetailRepository;
	
	public RefundServiceImpl(TransactionsRepository transactionsRepository,
			RefundDetailsRepository refundDetailsRepository,RefundUtility refundUtility,EmailUtil emailUtil,
			NotificationTemplateRepository notificationTemplateRepository,
			JuspayRedeemDetailRepository juspayRedeemDetailRepository,ConfigurationRepository configurationRepository,GiftcardRedeemDetailRepository giftcardRedeemDetailRepository) {

		this.transactionsRepository = transactionsRepository;
		this.refundDetailsRepository = refundDetailsRepository;
		this.refundUtility = refundUtility;
		this.emailUtil = emailUtil;
		this.notificationTemplateRepository = notificationTemplateRepository;
		this.configurationRepository = configurationRepository;
		this.juspayRedeemDetailRepository = juspayRedeemDetailRepository;
		this.giftcardRedeemDetailRepository = giftcardRedeemDetailRepository;

	}

	@Override
	public ResponseEntity<Object> initiateRefund(SingleRefundReq singleRefundReq) {
		Transactions transactions = this.getTransactions(singleRefundReq);
		if (Objects.nonNull(transactions)) {
			
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
						.result(Result.ERROR)
						.responseCode(RespCode.FAILED)
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
						"", template.getEmailCc(), template.getEmailBcc(), new Date(), 5, template.getName(),
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

	private Transactions getTransactions(SingleRefundReq singleRefundReq) {
		Optional<Transactions> opsTransactions = transactionsRepository.findById(singleRefundReq.getBookingId());
		if (opsTransactions.isPresent()) {
			return opsTransactions.get();
		}
		return Transactions.builder().build();
		
	}
	
	private Object cancelGiftCard(String bookingId) {
		try {
			GiftcardRedeemDetail giftcardRedeemDetail = giftcardRedeemDetailRepository.findByBookingId(bookingId);
			if(Objects.isNull(giftcardRedeemDetail)) {
				log.error("Booking not found for booking id: {}", bookingId);
				return null;
			}
			generateGiftCardToken(); //generate new token for gift card
			Map<String, Object> cancelResponse = refundUtility.cancelGiftCard(giftcardRedeemDetail);
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
	
	public void generateGiftCardToken() {
		String authToken = "";
		try {
			Map<String, Object> tokenResponse = refundUtility.generateGiftCardToken();
			log.info("Token response: " ,new Gson().toJson(tokenResponse));
			if(Objects.nonNull(tokenResponse) && 0 == (int) tokenResponse.get("responseCode") && "Transaction successful.".equalsIgnoreCase((String) tokenResponse.get("responseMessage"))){
				authToken = (String) tokenResponse.get("authToken");
				Configuration value = configurationRepository.findByName("GIFT_CARD_TOKEN");
				if(Objects.nonNull(value)) {
					value.setValue(authToken);
					configurationRepository.save(value);
				}
				//cancelGiftCard("200250006950751");
			}else {
				ResponseEntity.ok(null);
				log.info("failed to generate token, response: " ,new Gson().toJson(tokenResponse));
			}
			//return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			log.error("GIFT CARD ERROR: ", e);
		}
		//return ResponseEntity.ok(null);
	}
	
	

	@Override
	public ResponseEntity<Object> aproval(SingleRefundReq singleRefundReq) {
		Transactions transactions = this.getTransactions(singleRefundReq);
		if (Objects.nonNull(transactions)) {
			this.juspay(singleRefundReq.getBookingId());
			
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
				.responseCode(RespCode.FAILED)
				.message(Message.ALREADY_PROCESSED)
				.build());
	}

	private void juspay(String bookingId) {
		JuspayOrderStatus juspayOrderStatus = refundUtility.juspayOrderStatus(bookingId);
		if(!ObjectUtils.isEmpty(juspayOrderStatus.getStatus()) && juspayOrderStatus.getStatus().equalsIgnoreCase("CHARGED")) {
			
		}
	}
	
	public boolean jusPayRefund(String bookingid, String type, String amt, Transactions booking) {
        boolean isrollback = true;
        try {
            final List<JuspayRedeemDetail> redeemDetailVOs = juspayRedeemDetailRepository.findAllByBookingid(bookingid);
            if (redeemDetailVOs != null && !redeemDetailVOs.isEmpty()) {
                for (JuspayRedeemDetail redeemDetailVO : redeemDetailVOs) {
                    log.debug("REFUND PROCESSING FOR BOOKING ID :: {}, PAYMENT ID :: {}", bookingid, redeemDetailVO.getId());
                    final JuspayOrderStatus orderStatusVO = refundUtility.juspayOrderStatus(redeemDetailVO.getBookingid());
                    if (orderStatusVO != null && orderStatusVO.getStatus().equalsIgnoreCase("CHARGED")) {
                        log.debug("JUSPAY ORDER STATUS RESULT FOR BOOKING ID :: {}", orderStatusVO.getStatus(), redeemDetailVO.getBookingid());
                       
                        if (orderStatusVO.getRefunds() != null && orderStatusVO.getRefunds().size() > 0) {
                            JuspayRefund refundVO = orderStatusVO.getRefunds().get(0);
                            if (refundVO != null && (refundVO.isSent_to_gateway() || orderStatusVO.getRefunded())) {
                            	redeemDetailVO.setStatus(Constants.RGM_CANCEL);
                                redeemDetailVO.setRefunded(orderStatusVO.getRefunded());
                                redeemDetailVO.setRefundAmount(String.valueOf(refundVO.getAmount()));
                                redeemDetailVO.setRefundDate(new Date());
                                redeemDetailVO.setRefundId(refundVO.getRef());
                                redeemDetailVO.setResponseMessage(refundVO.getStatus());
                                juspayRedeemDetailRepository.save(redeemDetailVO);
                                log.debug("amount has already been refunded successfully for booking id :: {}, payment id :: {}", bookingid, redeemDetailVO.getId());
                            } else {
                                log.debug("refunded amount for booking id :: {} :: {}", bookingid, amt);
                                final JuspayRefundResponse refundResponseVO = refundUtility.juspayOrderRefund(orderStatusVO.getOrder_id(), amt);
                                if (refundResponseVO != null) {
                                    log.debug("juspay refund order status for booking id = {} :: refunded = {}", redeemDetailVO.getBookingid(), refundResponseVO.isRefunded());
                                    JuspayRefund refundVO2 = refundResponseVO.getRefunds().get(0);
                                    if (refundVO2 != null && (refundVO2.isSent_to_gateway() || refundResponseVO.isRefunded())) {
                                    	redeemDetailVO.setStatus(Constants.RGM_CANCEL);
                                        redeemDetailVO.setRefunded(refundResponseVO.isRefunded());
                                        
                                        redeemDetailVO.setRefundAmount(String.valueOf(refundVO2.getAmount()));
                                        redeemDetailVO.setRefundDate(new Date());
                                        redeemDetailVO.setRefundId(refundVO2.getRef());
                                        redeemDetailVO.setResponseMessage(refundVO2.getStatus());
                                        juspayRedeemDetailRepository.save(redeemDetailVO);
                                        log.debug("amount has been refunded successfully for booking id :: {}, payment id :: {}", bookingid, redeemDetailVO.getId());
                                    } else {
                                        isrollback = false;
                                    }
                                } else {
                                	log.debug("juspay refund result is null for booking id :: {}", redeemDetailVO.getBookingid());
                                    isrollback = false;
                                }
                            }
                        } else {
                        	log.debug("refunded amount for booking id :: {} :: {}", bookingid, amt);
                            final JuspayRefundResponse refundResponseVO = refundUtility.juspayOrderRefund(orderStatusVO.getOrder_id(), amt);
                            if (refundResponseVO != null) {
                            	log.debug("juspay refund order status for booking id = {} :: refunded = {}", redeemDetailVO.getBookingid(), refundResponseVO.isRefunded());
                                JuspayRefund refundVO2 = refundResponseVO.getRefunds() != null ? refundResponseVO.getRefunds().get(0) : null;
                                if (refundVO2 != null && (refundVO2.isSent_to_gateway() || refundResponseVO.isRefunded())) {
                                	redeemDetailVO.setStatus(Constants.RGM_CANCEL);
                                    redeemDetailVO.setRefunded(refundResponseVO.isRefunded());
                                    redeemDetailVO.setRefundAmount(String.valueOf(refundVO2.getAmount()));
                                    redeemDetailVO.setRefundDate(new Date());
                                    redeemDetailVO.setRefundId(refundVO2.getRef());
                                    redeemDetailVO.setResponseMessage(refundVO2.getStatus());
                                    juspayRedeemDetailRepository.save(redeemDetailVO);
                                    log.debug("amount has been refunded successfully for booking id :: {}, PAYMENT ID :: {}", bookingid, redeemDetailVO.getId());
                                } else {
                                    isrollback = false;
                                }
                            } else {
                            	log.debug("juspay refund result is null for booking id :: {}", redeemDetailVO.getBookingid());
                                isrollback = false;
                            }
                        }
                    } else if (orderStatusVO != null && (orderStatusVO.getStatus().equalsIgnoreCase("AUTHORIZING") || orderStatusVO.getStatus().equalsIgnoreCase("PENDING_VBV"))) {
                        booking.setPaymentStatus(orderStatusVO.getStatus());
                        transactionsRepository.save(booking);
                        isrollback = false;
                    } else {
                    	log.debug("juspay order status result for booking id :: {}", redeemDetailVO.getBookingid(), orderStatusVO.getStatus());
                    }
                    //}
                }
            } else {
            	log.debug("no payment to refund for this booking :: {}", bookingid);
            }
        } catch (final Exception e) {
        	log.error("exception occured in juspay rollback for booking id :: {} :: {}", bookingid, e.getMessage());
            isrollback = false;
        }
        return isrollback;
    }


}
