package com.cinema.crm.modules.refunds.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.InitiateRefundResponse;
import com.cinema.crm.constants.Constants.Message;
import com.cinema.crm.constants.Constants.RespCode;
import com.cinema.crm.constants.Constants.Result;
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

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RefundServiceImpl implements RefundService {

	private final TransactionsRepository transactionsRepository;
	private final RefundDetailsRepository refundDetailsRepository;
	private final RefundUtility refundUtility;
	private final EmailUtil emailUtil;
	private final NotificationTemplateRepository notificationTemplateRepository;
	
	public RefundServiceImpl(TransactionsRepository transactionsRepository,
			RefundDetailsRepository refundDetailsRepository,RefundUtility refundUtility,EmailUtil emailUtil,
			NotificationTemplateRepository notificationTemplateRepository) {
		this.transactionsRepository = transactionsRepository;
		this.refundDetailsRepository = refundDetailsRepository;
		this.refundUtility = refundUtility;
		this.emailUtil = emailUtil;
		this.notificationTemplateRepository = notificationTemplateRepository;
	}

	@Override
	public ResponseEntity<Object> signleRefund(SingleRefundReq singleRefundReq) {
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		Optional<Transactions> opsTransactions = transactionsRepository.findById(singleRefundReq.getBookingId());
		if (opsTransactions.isPresent()) {
			Transactions transactions = opsTransactions.get();
			if (Constants.REFUND_INITIATE.equals(transactions.getRefundStatus())) {
				return ResponseEntity.ok(InitiateRefundResponse.builder()
						.bookingId(singleRefundReq.getBookingId())
						.result(Result.ERROR)
						.responseCode(RespCode.FAILED)
						.message(Message.ALREADY_REQUESTED_NODAL_OFFICER)
						.build());
			}
			
			if (Constants.REFUND_COMPLETED.equals(transactions.getRefundStatus())) {
				return ResponseEntity.ok(InitiateRefundResponse.builder()
						.bookingId(singleRefundReq.getBookingId())
						.result(Result.SUCCESS)
						.responseCode(RespCode.SUCCESS)
						.message(Message.ALREADY_PROCESSED_NODAL_OFFICER)
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
			
			//TODO EMAIL SEND TO NODAL OFFICER
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
				transactions.setRefundStatus(Constants.REFUND_INITIATE);
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


}
