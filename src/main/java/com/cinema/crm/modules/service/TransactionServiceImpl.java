package com.cinema.crm.modules.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.databases.pvrinox.entities.OrderBooking;
import com.cinema.crm.databases.pvrinox.entities.Transactions;
import com.cinema.crm.databases.pvrinox.repositories.OrderBookingRepository;
import com.cinema.crm.databases.pvrinox.repositories.TransactionsRepository;
import com.cinema.crm.databases.pvrinoxcrm.entities.RefundDetails;
import com.cinema.crm.databases.pvrinoxcrm.repositories.RefundDetailsRepository;
import com.cinema.crm.modules.model.TransactionReq;
import com.cinema.crm.modules.model.TransactionResp;
import com.cinema.crm.modules.model.WSReturnObj;

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
			
			if(StringUtils.isEmpty(transactionReq.getMobile()) && StringUtils.isEmpty(transactionReq.getBookingId())) {
				log.error("Mobile number and Booking id both can not be empty.");
				returnObj.setMsg("Mobile number and Booking id both can not be empty.");
				returnObj.setOutput(null);
				returnObj.setResponseCode(201);
				returnObj.setResult("error");
				return ResponseEntity.ok(returnObj);
			}
			List<TransactionResp> responseList = new ArrayList<>();
			if(transactionReq.requested) {
				List<RefundDetails> refundList = refundDetailsRepository.findAll();
				returnObj.setMsg(refundList.isEmpty() ? "No Data Found" : "success");
				returnObj.setOutput(refundList);
				returnObj.setResponseCode(200);
				returnObj.setResult("sucess");
				log.info("Transaction data {} :: ",refundList.isEmpty() ? "No Data Found " : "success " , refundList);
				return ResponseEntity.ok(returnObj);
			}else {
				String stringQuery = buildTransactionData(transactionReq);
				TypedQuery<Transactions> query = entityManager.createQuery(stringQuery, Transactions.class);
				transactionQueryParamSetter(query, transactionReq);
				List<Transactions> orderBookingList = query.getResultList();
				responseList = orderBookingList.stream().map(this::convertToTransactionResp).toList();
					returnObj.setMsg(responseList.isEmpty() ? "No Data Found" : "success");
					returnObj.setOutput(responseList);
					returnObj.setResponseCode(200);
					returnObj.setResult("sucess");
					log.info("Transaction data {} :: ",responseList.isEmpty() ? "No Data Found " : "success " , responseList);
					return ResponseEntity.ok(returnObj);
			}

		} catch (Exception e) {
			log.error("Exception occured in getAllTransactions {} : ",e);
			returnObj.setMsg("error");
			returnObj.setOutput(null);
			returnObj.setResponseCode(500);
			returnObj.setResult("error");
			return ResponseEntity.ok(returnObj);
		}

	}

	private TransactionResp convertToTransactionResp(Transactions order) {
		Optional<RefundDetails> data = refundDetailsRepository.findByBookingId(order.getId());
		RefundDetails refundDetails = new RefundDetails();
		if(data.isPresent()) {
			refundDetails = data.get();
		}
		TransactionResp resp = new TransactionResp();
		resp.bookingId = order.getId();
		resp.eventName = order.getFilmName();
		resp.orderId = order.getBookingId();
		resp.mobile = order.getMobile();
		resp.seatNumber = order.getSeats();
		resp.paymodes = order.getPaymode();
		resp.vouchers = order.getCvpVouchers();
		resp.voucherStatus = "";
		resp.cancelReasons = refundDetails.getRemarks();
		resp.cancelDate = order.getCreatedAt().toString();
		resp.refunded = false;
		resp.totalAmount = order.getTotalTicketPrice() + order.getFbTotalPrice();
		resp.approval = "";
		resp.refundStatus = refundDetails.getRefundStatus();
		resp.utrNumber = "";
		resp.customerName = order.getName();
		return resp;
	}

	private String buildTransactionData(TransactionReq transactionReq) {

		String baseQuery = "SELECT o FROM Transactions o WHERE ";
		StringBuilder queryBuilder = new StringBuilder(baseQuery);

		if (StringUtils.isNotBlank(transactionReq.getMobile())) {
			queryBuilder.append(" o.mobile = :mobile order by bookingTime desc limit 7");
		}

		if (StringUtils.isNotBlank(transactionReq.getBookingId())) {
			queryBuilder.append(" o.bookingId = :bookingId order by bookingTime desc limit 7");
		}

//		if (StringUtils.isNotBlank(transactionReq.getCinema())) {
//			queryBuilder.append("AND o.theaterId = :theaterId ");
//		}
//
//		if (StringUtils.isNotBlank(transactionReq.getOrderId())) {
//			queryBuilder.append("AND o.id = :id ");
//		}
//
//		if (StringUtils.isNotBlank(transactionReq.getFromDate())
//				&& StringUtils.isNotBlank(transactionReq.getUptoDate())) {
//			queryBuilder.append("AND o.bookingTime BETWEEN :startDate AND :endDate");
//		}

		return queryBuilder.toString();
	}

	private void transactionQueryParamSetter(Object query, TransactionReq transactionReq) {

		if (StringUtils.isNoneBlank(transactionReq.getMobile())) {
			((Query) query).setParameter("mobile", transactionReq.getMobile());
		}
		if (StringUtils.isNoneBlank(transactionReq.getBookingId())) {
			((Query) query).setParameter("bookingId", transactionReq.getBookingId());
		}
//		if (StringUtils.isNoneBlank(transactionReq.getCinema())) {
//			((Query) query).setParameter("theaterId", transactionReq.getCinema());
//		}
//		if (StringUtils.isNoneBlank(transactionReq.getOrderId())) {
//			((Query) query).setParameter("id", transactionReq.getOrderId());
//		}
//
//		if (Objects.nonNull(start)) {
//			((Query) query).setParameter("startDate", start);
//		}
//
//		if (Objects.nonNull(end)) {
//			((Query) query).setParameter("endDate", end);
//		}
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
				log.info("Session Transaction data no record found {} :: " , booking);
				return ResponseEntity.ok(returnObj);
			} else {
				returnObj.setMsg("success");
				returnObj.setOutput(booking);
				returnObj.setResponseCode(200);
				returnObj.setResult("sucess");
				log.info("Session Transaction data {} :: " , booking);
				return ResponseEntity.ok(returnObj);
			}

		} catch (Exception e) {
			returnObj.setMsg("error");
			returnObj.setOutput(null);
			returnObj.setResponseCode(500);
			returnObj.setResult("error");
			log.error("Exception occured in Session Transaction {} :: ",e);
			return ResponseEntity.ok(returnObj);
		}
	}

}
