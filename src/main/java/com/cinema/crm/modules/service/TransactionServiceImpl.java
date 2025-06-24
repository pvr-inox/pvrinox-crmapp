package com.cinema.crm.modules.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.logging.Messages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.Constants.Message;
import com.cinema.crm.constants.Constants.RespCode;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.constants.InitiateRefundResponse;
import com.cinema.crm.modules.entity.OrderBooking;
import com.cinema.crm.modules.entity.OrderBookingRepository;
import com.cinema.crm.modules.entity.RefundDetails;
import com.cinema.crm.modules.entity.Transactions;
import com.cinema.crm.modules.model.SingleRefundReq;
import com.cinema.crm.modules.model.TransactionReq;
import com.cinema.crm.modules.model.TransactionResp;
import com.cinema.crm.modules.model.WSReturnObj;
import com.cinema.crm.modules.repository.RefundDetailsRepository;
import com.cinema.crm.modules.repository.TransactionsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
	private final EntityManager entityManager;
	private final OrderBookingRepository orderBookingRepository;
	private final TransactionsRepository transactionsRepository;
	private final RefundDetailsRepository refundDetailsRepository;

	public TransactionServiceImpl(EntityManager entityManager, OrderBookingRepository orderBookingRepository,TransactionsRepository transactionsRepository,RefundDetailsRepository refundDetailsRepository) {
		this.entityManager = entityManager;
		this.orderBookingRepository = orderBookingRepository;
		this.transactionsRepository = transactionsRepository;
		this.refundDetailsRepository = refundDetailsRepository;
	}

	@Override
	public ResponseEntity<Object> getAllTransactions(TransactionReq transactionReq) {
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		try {

			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

			Date start = StringUtils.isNoneEmpty(transactionReq.getFromDate())
					? formater.parse(transactionReq.getFromDate())
					: null;
			Date end = StringUtils.isNoneEmpty(transactionReq.getUptoDate())
					? formater.parse(transactionReq.getUptoDate())
					: null;

			String stringQuery = buildTransactionData(transactionReq);
			TypedQuery<OrderBooking> query = entityManager.createQuery(stringQuery, OrderBooking.class);
			transactionQueryParamSetter(query, start, end, transactionReq);

			List<OrderBooking> orderBookingList = query.getResultList();
			List<TransactionResp> responseList = orderBookingList.stream().map(this::convertToTransactionResp).toList();

			returnObj.setMsg("success");
			returnObj.setOutput(responseList);
			returnObj.setResponseCode(200);
			returnObj.setResult("sucess");
			return ResponseEntity.ok(returnObj);

		} catch (Exception e) {
			returnObj.setMsg("error");
			returnObj.setOutput(null);
			returnObj.setResponseCode(500);
			returnObj.setResult("error");
			return ResponseEntity.ok(returnObj);

		}

	}

	private TransactionResp convertToTransactionResp(OrderBooking order) {
		TransactionResp resp = new TransactionResp();
		resp.bookingId = order.getBookingId();
		resp.eventName = order.getOrderFilmCinema().getFilmName();
		resp.orderId = order.getOrderIdEx();
		resp.mobile = order.getMobile();
		resp.seatNumber = order.getOrderTicket().getSeats();
		resp.paymodes = order.getPaymode();
		resp.vouchers = order.getOrderTicket().getCvpVouchers();
		resp.voucherStatus = "";
		resp.cancelReasons = "";
		resp.cancelDate = order.getModifiedAt().toGMTString();
		resp.refunded = false;
		resp.totalAmount = order.getTotalPrice();
		resp.approval = "";
		resp.refundStatus = "";
		resp.utrNumber = "";
		resp.customerName = order.getName();

		return resp;
	}

	private String buildTransactionData(TransactionReq transactionReq) {

		String baseQuery = "SELECT o FROM OrderBooking o WHERE bookingStatus = 'BOOKED' ";
		StringBuilder queryBuilder = new StringBuilder(baseQuery);

		if (StringUtils.isNotBlank(transactionReq.getMobile())) {
			queryBuilder.append("AND o.mobile = :mobile ");
		}

		if (StringUtils.isNotBlank(transactionReq.getBookingId())) {
			queryBuilder.append("AND o.bookingId = :bookingId ");
		}

		if (StringUtils.isNotBlank(transactionReq.getCinema())) {
			queryBuilder.append("AND o.theaterId = :theaterId ");
		}

		if (StringUtils.isNotBlank(transactionReq.getOrderId())) {
			queryBuilder.append("AND o.id = :id ");
		}

		if (StringUtils.isNotBlank(transactionReq.getFromDate())
				&& StringUtils.isNotBlank(transactionReq.getUptoDate())) {
			queryBuilder.append("AND o.bookingTime BETWEEN :startDate AND :endDate");
		}

		return queryBuilder.toString();
	}

	private void transactionQueryParamSetter(Object query, Date start, Date end, TransactionReq transactionReq) {

		if (StringUtils.isNoneBlank(transactionReq.getMobile())) {
			((Query) query).setParameter("mobile", transactionReq.getMobile());
		}
		if (StringUtils.isNoneBlank(transactionReq.getBookingId())) {
			((Query) query).setParameter("bookingId", transactionReq.getBookingId());
		}
		if (StringUtils.isNoneBlank(transactionReq.getCinema())) {
			((Query) query).setParameter("theaterId", transactionReq.getCinema());
		}
		if (StringUtils.isNoneBlank(transactionReq.getOrderId())) {
			((Query) query).setParameter("id", transactionReq.getOrderId());
		}

		if (Objects.nonNull(start)) {
			((Query) query).setParameter("startDate", start);
		}

		if (Objects.nonNull(end)) {
			((Query) query).setParameter("endDate", end);
		}
	}

	@Override
	public ResponseEntity<Object> signleRefund(SingleRefundReq singleRefundReq) {
		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		Optional<Transactions> opsTransactions = transactionsRepository.findById(singleRefundReq.getBookingId());
		if (opsTransactions.isPresent()) {
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
					.refundStatus(Constants.INITIATE)
					.build();
			refundDetailsRepository.save(refundDetails);
			
			
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

	@Override
	public ResponseEntity<Object> getSessionTtrans(long sessionId) {

		WSReturnObj<Object> returnObj = new WSReturnObj<>();
		try {
			List<OrderBooking> booking = orderBookingRepository.findByOrderTicketSessionId(sessionId);
			if (booking.isEmpty()) {
				returnObj.setMsg("no record found");
				returnObj.setOutput(null);
				returnObj.setResponseCode(200);
				returnObj.setResult("sucess");
				return ResponseEntity.ok(returnObj);
			} else {
				returnObj.setMsg("success");
				returnObj.setOutput(booking);
				returnObj.setResponseCode(200);
				returnObj.setResult("sucess");
				return ResponseEntity.ok(returnObj);
			}

		} catch (Exception e) {
			returnObj.setMsg("error");
			returnObj.setOutput(null);
			returnObj.setResponseCode(500);
			returnObj.setResult("error");
			return ResponseEntity.ok(returnObj);
		}
	}

}
