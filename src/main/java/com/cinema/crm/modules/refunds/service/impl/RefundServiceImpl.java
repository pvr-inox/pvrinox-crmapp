package com.cinema.crm.modules.refunds.service.impl;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.Constants.Foodtype;
import com.cinema.crm.constants.Constants.Message;
import com.cinema.crm.constants.Constants.RespCode;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.constants.Constants.ShowBizTransType;
import com.cinema.crm.constants.Constants.TransType;
import com.cinema.crm.constants.InitiateRefundResponse;
import com.cinema.crm.databases.pvrinox.entities.Cinema;
import com.cinema.crm.databases.pvrinox.entities.Configuration;
import com.cinema.crm.databases.pvrinox.entities.Email;
import com.cinema.crm.databases.pvrinox.entities.GiftcardRedeemDetail;
import com.cinema.crm.databases.pvrinox.entities.GyftrRedeemDetail;
import com.cinema.crm.databases.pvrinox.entities.JuspayRedeemDetail;
import com.cinema.crm.databases.pvrinox.entities.OrderBooking;
import com.cinema.crm.databases.pvrinox.entities.Pos;
import com.cinema.crm.databases.pvrinox.entities.Transactions;
import com.cinema.crm.databases.pvrinox.repositories.CinemaRepository;
import com.cinema.crm.databases.pvrinox.repositories.ConfigurationRepository;
import com.cinema.crm.databases.pvrinox.repositories.GiftcardRedeemDetailRepository;
import com.cinema.crm.databases.pvrinox.repositories.GyftrRedeemDetailRepository;
import com.cinema.crm.databases.pvrinox.repositories.JuspayRedeemDetailRepository;
import com.cinema.crm.databases.pvrinox.repositories.OrderBookingRepository;
import com.cinema.crm.databases.pvrinox.repositories.PosRepository;
import com.cinema.crm.databases.pvrinox.repositories.TransactionsRepository;
import com.cinema.crm.databases.pvrinoxcrm.entities.NotificationTemplate;
import com.cinema.crm.databases.pvrinoxcrm.entities.RefundDetails;
import com.cinema.crm.databases.pvrinoxcrm.repositories.NotificationTemplateRepository;
import com.cinema.crm.databases.pvrinoxcrm.repositories.RefundDetailsRepository;
import com.cinema.crm.modules.model.JuspayOrderStatus;
import com.cinema.crm.modules.model.JuspayRefund;
import com.cinema.crm.modules.model.OrderCancelData;
import com.cinema.crm.modules.model.OrderCommitData;
import com.cinema.crm.modules.model.ReqRefundTrans;
import com.cinema.crm.modules.model.SingleRefundRequest;
import com.cinema.crm.modules.refunds.model.GyftrCancelationResponse;
import com.cinema.crm.modules.refunds.model.JuspayRefundResponse;
import com.cinema.crm.modules.refunds.service.RefundService;
import com.cinema.crm.modules.utils.EmailUtil;
import com.cinema.crm.modules.utils.RefundUtility;
import com.cinema.crm.modules.utils.ShowbizUtil;
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
	private final GyftrRedeemDetailRepository gyftrRedeemDetailRepository;
	private final ShowbizUtil showbizUtil;
	private final CinemaRepository cinemaRepository;
	private final PosRepository posRepository;
	private final OrderBookingRepository orderBookingRepository;
	
	
	public RefundServiceImpl(TransactionsRepository transactionsRepository,
			RefundDetailsRepository refundDetailsRepository,RefundUtility refundUtility,EmailUtil emailUtil,
			NotificationTemplateRepository notificationTemplateRepository,
			JuspayRedeemDetailRepository juspayRedeemDetailRepository,
			ConfigurationRepository configurationRepository,
			GiftcardRedeemDetailRepository giftcardRedeemDetailRepository,
			GyftrRedeemDetailRepository gyftrRedeemDetailRepository,
			ShowbizUtil showbizUtil,CinemaRepository cinemaRepository,PosRepository posRepository,
			OrderBookingRepository orderBookingRepository) {

		this.transactionsRepository = transactionsRepository;
		this.refundDetailsRepository = refundDetailsRepository;
		this.refundUtility = refundUtility;
		this.emailUtil = emailUtil;
		this.notificationTemplateRepository = notificationTemplateRepository;
		this.configurationRepository = configurationRepository;
		this.juspayRedeemDetailRepository = juspayRedeemDetailRepository;
		this.giftcardRedeemDetailRepository = giftcardRedeemDetailRepository;
		this.gyftrRedeemDetailRepository = gyftrRedeemDetailRepository;
		this.showbizUtil = showbizUtil;
		this.cinemaRepository = cinemaRepository;
		this.posRepository = posRepository;
		this.orderBookingRepository = orderBookingRepository;
	}

	@Override
	public ResponseEntity<Object> initiateRefund(SingleRefundRequest singleRefundReq) {
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
//				transactions.setPaymentStatus(Constants.CRM_ROLLEDBACK);
				transactions.setBookingStatus(Constants.REFUND_INITIATE);
				transactionsRepository.save(transactions);
			}
			
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

	private Transactions getTransactions(SingleRefundRequest singleRefundReq) {
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
	public ResponseEntity<Object> aproval(SingleRefundRequest singleRefundReq) {
		
		Transactions transactions = this.getTransactions(singleRefundReq);
		if (Objects.nonNull(transactions) && transactions.getBookingStatus().contains(Constants.CANCEL)) {
			return ResponseEntity.ok(InitiateRefundResponse.builder()
					.bookingId(singleRefundReq.getBookingId())
					.result(Result.ERROR)
					.responseCode(RespCode.FAILED)
					.message(Message.ALREADY_PROCESSED)
					.build());
		}

		if (Objects.nonNull(transactions) && transactions.getBookingStatus().equals(Constants.REFUND_INITIATE)) {
			boolean refunded = false;
			if(transactions.getJuspayused()) {
				refunded = this.jusPayRefund(singleRefundReq.getBookingId(), singleRefundReq.getRefundAmount(), transactions, true);
			}
			
			if (transactions.getGyfterused()) {
				refunded = this.gyftrRefund(singleRefundReq.getBookingId(), transactions);
			}
			
			if (refunded) {
				return ResponseEntity.ok(InitiateRefundResponse.builder()
						.bookingId(singleRefundReq.getBookingId())
						.result(Result.SUCCESS)
						.responseCode(RespCode.SUCCESS)
						.message(Message.REFUND_REQUEST_RAISED)
						.build());
			}
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
				.message(Message.BOOKING_NOT_FOUNDED)
				.build());
	}
	
	public OrderCancelData cancelShowbizTransaction(String bookingId) {
        try {
        	Transactions request = transactionsRepository.findById(bookingId).get();
        	
        	if(Objects.isNull(request)) {
        		 return null;
        	}
        	OrderCommitData orderData = new OrderCommitData();
        	Cinema cinema = cinemaRepository.findByTheatreId(request.getTheaterId());
        	Pos pos = posRepository.findByName(cinema.getPos());
        	String transType = ShowBizTransType.Normal;
			if (request.getBookType().equals(TransType.FOOD)) {
				if (request.getFoodType().equals(Foodtype.ONSEAT)) {
					transType = ShowBizTransType.InCinemaFB;
				} else {
					transType = ShowBizTransType.OnlyFB;
				}
			}
        	// ticket verify
        	if(request.getBookType().equals("BOOKING")) {
        		String soldStatusResponse = showbizUtil.soldStatus(cinema, pos, request.getPlatform(), request.getChain(), request.getTransId());
            	if (StringUtils.hasText(soldStatusResponse)) {
                    orderData = showbizUtil.getShowBizOrderDataVerify(soldStatusResponse);
                    orderData.setCinemaId(cinema.getTheatreId());
                    orderData.setChain(cinema.getChainKey());
                    orderData.setPos(cinema.getPos());
					if (orderData.isSuccess()) {
						log.info("showbiz soldStatus success:: {}", new Gson().toJson(orderData));
					} else {
						return null;
					}
                }
        	}else {
        		// food verify
    			String soldStatusFbResponse =  showbizUtil.soldStatusFb(cinema, pos,request.getPlatform(), request.getChain(), request.getTransId(),transType);
            	if (StringUtils.hasText(soldStatusFbResponse)) {
                    orderData = showbizUtil.getShowBizOrderDataVerifyFood(soldStatusFbResponse);
                    orderData.setCinemaId(cinema.getTheatreId());
                    orderData.setChain(cinema.getChainKey());
                    orderData.setPos(cinema.getPos());
					if (orderData.isSuccess()) {
						log.info("showbiz soldStatusFb success:: {}", new Gson().toJson(orderData));
					} else {
						return null;
					}
                } 
        	}
        	
        	ReqRefundTrans req = new ReqRefundTrans();
        	req.setCcode(request.getTheaterId());
        	req.setPlatform(request.getPlatform());
        	req.setChain(request.getChain());
        	req.setBookId(String.valueOf(request.getOrderId()));
        	req.setCardNumber("1234567890000000");
        	req.setCustomerName(request.getName());
        	DecimalFormat decimalFormat = new DecimalFormat("#.00");
        	req.setReceiptNo(request.getBookingId());
        	req.setRemarks("User Cancellation");
        	LocalDateTime showTime = request.getShowTime();
        	Date showTimeDate = Date.from(showTime.atZone(ZoneId.systemDefault()).toInstant());
        	DateFormat sDdate = new SimpleDateFormat("yyyy-MM-dd");
        	DateFormat stdate = new SimpleDateFormat("HH:mm:ss");
        	req.setShowDate(sDdate.format(showTimeDate));
        	req.setShowTime(stdate.format(showTimeDate));
        	req.setScreenId(request.getScreenId());
        	req.setNoOfTicket(request.getNumOfSeats());
        	if (request.getNumOfSeats() != 0) {
        	    req.setRate(decimalFormat.format((double) request.getTicketPrice() / 100));
        	    req.setValue(decimalFormat.format((double) request.getTotalTicketPrice() / 100));
        	} else {
        	    req.setRate("0.00");
        	    req.setValue("0.00");
        	}
        	req.setFbNoOfItems(request.getFbCount());
        	if (request.getFbCount() != 0) {
        	    req.setFbValue(decimalFormat.format((double) request.getFbTotalPrice() / 100));
        	} else {
        	    req.setFbValue("0.00");
        	}
        	req.setPgId("0");
        	if (request.getBookType().equalsIgnoreCase(Constants.ORDER_TYPE.booking.toString())) {
        	    req.setTransType("Normal");
        	} else if (request.getBookType().equalsIgnoreCase(Constants.ORDER_TYPE.food.toString())) {
        	    req.setTransType(Constants.ShowBizTransType.OnlyFB.toString());
        	}
        	    
        	  String cancelBuyResponse = showbizUtil.cancelBuy(cinema, pos, req);
				if (StringUtils.hasText(cancelBuyResponse)) {
					OrderCancelData orderCancelData = showbizUtil.getShowBizCancelBuyData(cancelBuyResponse, cinema,
							req);
					log.info("REFUND DATA::" + new Gson().toJson(orderCancelData));
					if (orderCancelData.isSuccess()) {
						log.info("showbiz cancelBuy success:: {}" , new Gson().toJson(orderCancelData));
						return orderCancelData;
					} else {
						// error
						log.error("showbiz cancelBuy error message:: {}" , new Gson().toJson(orderCancelData.getErrorMsg()));
						log.error("showbiz cancelBuy error:: {}" , new Gson().toJson(orderCancelData));
						return orderCancelData;
					}
				}

        } catch (Exception e) {
        	log.error("exception occured in showbiz cancel for booking id : {} :: {}", bookingId ,  e.getMessage());
        }
        return null;
    }

	public boolean jusPayRefund(String bookingid,String refundAmt, Transactions booking, boolean rollback) {
        try {
            final List<JuspayRedeemDetail> juspayRedeemDetails = juspayRedeemDetailRepository.findAllByBookingid(bookingid);
            if (juspayRedeemDetails != null && !juspayRedeemDetails.isEmpty()) {
                for (JuspayRedeemDetail juspayRedeemDetail : juspayRedeemDetails) {
                    log.debug("refund processing for booking id : {} payment id : {}", bookingid, juspayRedeemDetail.getId());
                    final JuspayOrderStatus orderStatusVO = refundUtility.juspayOrderStatus(juspayRedeemDetail.getBookingid());
                    if (orderStatusVO != null && orderStatusVO.getStatus().equalsIgnoreCase("CHARGED")) {
                        log.debug("juspay order status result for booking id : {} :{}", orderStatusVO.getStatus(), juspayRedeemDetail.getBookingid());
                        
                        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        
                        if (orderStatusVO.getRefunds() != null && orderStatusVO.getRefunds().size() > 0) {
                            JuspayRefund refundVO = orderStatusVO.getRefunds().get(0);
                            if (refundVO != null && (refundVO.isSent_to_gateway() || orderStatusVO.getRefunded())) {
                            	juspayRedeemDetail.setStatus(Constants.CANCEL_COMPLETE);
                                juspayRedeemDetail.setRefunded(orderStatusVO.getRefunded());
                                juspayRedeemDetail.setRefundAmount(String.valueOf(refundVO.getAmount()));
                                juspayRedeemDetail.setRefundDate(isoFormat.parse(orderStatusVO.getDate_created()));
                                juspayRedeemDetail.setRefundId(refundVO.getRef());
                                juspayRedeemDetail.setResponseMessage(refundVO.getStatus());
                                juspayRedeemDetailRepository.save(juspayRedeemDetail);
                                log.debug("amount has already been refunded successfully for booking id : {}, payment id : {}", bookingid, juspayRedeemDetail.getId());
                            } else {
                                log.debug("refunded amount for booking id : {} : {}", bookingid, refundAmt);
                                final JuspayRefundResponse refundResponseVO = refundUtility.juspayOrderRefund(orderStatusVO.getOrder_id(), refundAmt);
                                if (refundResponseVO != null) {
                                    log.debug("juspay refund order status for booking id = {} : refunded = {}", juspayRedeemDetail.getBookingid(), refundResponseVO.isRefunded());
                                    JuspayRefund refundVO2 = refundResponseVO.getRefunds().get(0);
                                    if (refundVO2 != null) {
                                    	juspayRedeemDetail.setStatus(Constants.CANCEL_COMPLETE);
                                        juspayRedeemDetail.setRefunded(refundResponseVO.isRefunded());
                                        
                                        juspayRedeemDetail.setRefundAmount(String.valueOf(refundVO2.getAmount()));
                                        juspayRedeemDetail.setRefundDate(new Date());
                                        juspayRedeemDetail.setRefundId(refundVO2.getRef());
                                        juspayRedeemDetail.setResponseMessage(refundVO2.getStatus());
                                        juspayRedeemDetailRepository.save(juspayRedeemDetail);
                                        booking.setBookingStatus(Constants.CANCEL_COMPLETE);
                                        booking.setPaymentStatus(Constants.ROLLEDBACK);
                                        booking.setCancelTime(LocalDateTime.now());
                                        transactionsRepository.save(booking);
                                        log.debug("amount has been refunded successfully for booking id : {}, payment id : {}", bookingid, juspayRedeemDetail.getId());
                                    }
                                }
                            }
                        } else {
                        	log.debug("refunded amount for booking id : {} : {}", bookingid, refundAmt);
                            final JuspayRefundResponse refundResponseVO = refundUtility.juspayOrderRefund(orderStatusVO.getOrder_id(), refundAmt);
                            if (refundResponseVO != null) {
                            	log.debug("juspay refund order status for booking id = {} : refunded = {}", juspayRedeemDetail.getBookingid(), refundResponseVO.isRefunded());
                                JuspayRefund refundVO2 = refundResponseVO.getRefunds() != null ? refundResponseVO.getRefunds().get(0) : null;
                                if (refundVO2 != null) {
                                	juspayRedeemDetail.setStatus(Constants.CANCEL_COMPLETE);
                                    juspayRedeemDetail.setRefunded(refundResponseVO.isRefunded());
                                    juspayRedeemDetail.setRefundAmount(String.valueOf(refundVO2.getAmount()));
                                    juspayRedeemDetail.setRefundDate(new Date());
                                    juspayRedeemDetail.setRefundId(refundVO2.getRef());
                                    juspayRedeemDetail.setResponseMessage(refundVO2.getStatus());
                                    juspayRedeemDetailRepository.save(juspayRedeemDetail);
                                    
                                    booking.setBookingStatus(Constants.CANCEL_COMPLETE);
                                    booking.setPaymentStatus(Constants.ROLLEDBACK);
                                    transactionsRepository.save(booking);
                                    log.debug("amount has been refunded successfully for booking id : {}, PAYMENT ID : {}", bookingid, juspayRedeemDetail.getId());
                                }
                            }
                        }
                    } else if (orderStatusVO != null && (orderStatusVO.getStatus().equalsIgnoreCase("AUTHORIZING") || orderStatusVO.getStatus().equalsIgnoreCase("PENDING_VBV"))) {
                        booking.setPaymentStatus(orderStatusVO.getStatus());
                        transactionsRepository.save(booking);
                        rollback = false;
                    }
                }
            }
        } catch (final Exception e) {
        	log.error("exception occured in juspay rollback for booking id : {} : {}", bookingid, e.getMessage());
            rollback = false;
        }
        return rollback;
    }


    private boolean gyftrRefund(String bookingId, Transactions booking) {
        boolean rollback = true;
        try {
            final List<GyftrRedeemDetail> list = gyftrRedeemDetailRepository.findAllByBookingId(bookingId);
            if (!CollectionUtils.isEmpty(list)) {
                for (GyftrRedeemDetail redeemDetail : list) {
                    GyftrCancelationResponse status = refundUtility.query(bookingId, redeemDetail.getCoupon());
                    if (!ObjectUtils.isEmpty(status) && status.getResultType().equalsIgnoreCase("SUCCESS") &&
                            status.getStatus().equalsIgnoreCase("CONSUMED")) {
                        GyftrCancelationResponse cancel = refundUtility.cancel(bookingId, redeemDetail.getCoupon());
                        if (!ObjectUtils.isEmpty(cancel) && (cancel.getResultType().equalsIgnoreCase("SUCCESS")
                                || cancel.getErrorCode().equalsIgnoreCase("E030"))) {
                            redeemDetail.setStatus(Constants.ROLLEDBACK);
                            gyftrRedeemDetailRepository.save(redeemDetail);
                            booking.setBookingStatus(Constants.CANCEL_COMPLETE);
                            booking.setPaymentStatus(Constants.ROLLEDBACK);
                            booking.setCancelTime(LocalDateTime.now());
                            transactionsRepository.save(booking);
                        } else {
                            rollback = false;
                        }
                    } 
                }
            }
        } catch (final Exception e) {
            log.error("exception occured in gyftr rollback for booking id : {} : {}", bookingId, e.getMessage());
            rollback = false;
        }
        return rollback;
    }
}
