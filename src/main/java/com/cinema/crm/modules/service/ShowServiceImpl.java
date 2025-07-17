package com.cinema.crm.modules.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.databases.pvrinoxcrm.entities.Show;
import com.cinema.crm.databases.pvrinoxcrm.entities.ShowTransaction;
import com.cinema.crm.databases.pvrinoxcrm.repositories.ShowRepository;
import com.cinema.crm.databases.pvrinoxcrm.repositories.ShowTransactionRepository;
import java.util.LinkedHashMap;

@Service
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;
	private final ShowTransactionRepository transactionRepository;

	public ShowServiceImpl(ShowRepository showRepository, ShowTransactionRepository transactionRepository) {
		this.showRepository = showRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public ResponseEntity<Object> saveShow(Show request) {
		Show show = new Show();

		// Set only required fields from Show
		show.setMovieId(request.getMovieId());
		show.setScreenId(request.getScreenId());
		show.setSessionId(request.getSessionId());
		show.setShowDateTime(request.getShowDateTime());
		show.setTheatreId(request.getTheatreId());
		show.setShowCancelDateTime(request.getShowCancelDateTime());
		show.setCancelationResone(request.getCancelationResone());
		show.setApprovedBy("Test");
		show.setRgmEmail("Test");

		// Prepare and attach manually set ShowTransaction list
		List<ShowTransaction> transactionList = new ArrayList<>();

		if (request.getTransactions() != null) {
			for (ShowTransaction tx : request.getTransactions()) {
				ShowTransaction transaction = new ShowTransaction();
				transaction.setUniqueRequestId(tx.getUniqueRequestId());
				transaction.setBookingId(tx.getBookingId());
				transaction.setRrn(tx.getRrn());
				transaction.setPayauthId(tx.getPayauthId());
				transaction.setBookingDateTime(tx.getBookingDateTime());
				transaction.setMobile(tx.getMobile());
				transaction.setUsername(tx.getUsername());
				transaction.setSeats(tx.getSeats());
				transaction.setShowClass(tx.getShowClass());
				transaction.setFnbCount(tx.getFnbCount());
				transaction.setFnbTotalPrice(tx.getFnbTotalPrice());
				transaction.setBalanceAmount(tx.getBalanceAmount());
				transaction.setConv(tx.getConv());
				transaction.setDiscount(tx.getDiscount());
				transaction.setDonation(tx.getDonation());
				transaction.setNumOfSeats(tx.getNumOfSeats());
				transaction.setTicketPrice(tx.getTicketPrice());
				transaction.setTotalTicketPrice(tx.getTotalTicketPrice());
				transaction.setPartner(tx.getPartner());
				transaction.setPaymode(tx.getPaymode());

				transaction.setShow(show); // Link to parent show
				transactionList.add(transaction);
			}
		}

		show.setTransactions(transactionList); // Set final transaction list

		// Save to DB
		showRepository.save(show);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Show and Transactions saved successfully.");
		// response.put("data", show.getMovieId());

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Object> getAllShowsWithRefundableFlag() {
	    List<Show> showsWithTransactions = showRepository.findAllWithTransactions(); // JOIN FETCH

	    Map<Long, Show> showMap = new LinkedHashMap<>();

	    for (Show show : showsWithTransactions) {
	        Long movieId = show.getMovieId();

	        // If already present, merge transactions
	        if (showMap.containsKey(movieId)) {
	            Show existingShow = showMap.get(movieId);
	            if (show.getTransactions() != null) {
	                for (ShowTransaction tx : show.getTransactions()) {
	                   // tx.setRefundable("INOX DIGITAL".equalsIgnoreCase(tx.getPartner()) && tx.isRefunded());
	                    tx.setShow(existingShow); // Relink
	                    existingShow.getTransactions().add(tx);
	                }
	            }
	        } else {
	            // Prepare new Show object
	            Show copiedShow = new Show();
	            copiedShow.setMovieId(show.getMovieId());
	            copiedShow.setMovieName(show.getMovieName());
	            copiedShow.setCertificate(show.getCertificate());
	            copiedShow.setFormat(show.getFormat());
	            copiedShow.setGenre(show.getGenre());
	            copiedShow.setLanguage(show.getLanguage());
	            copiedShow.setScreenId(show.getScreenId());
	            copiedShow.setScreenName(show.getScreenName());
	            copiedShow.setSessionId(show.getSessionId());
	            copiedShow.setShowDateTime(show.getShowDateTime());
	            copiedShow.setTheatreId(show.getTheatreId());
	            copiedShow.setSoldSeat(show.getSoldSeat());
	            copiedShow.setTotalSeat(show.getTotalSeat());
	            copiedShow.setCancelSeat(show.getCancelSeat());
	            copiedShow.setShowCancelDateTime(show.getShowCancelDateTime());
	            copiedShow.setCancelationResone(show.getCancelationResone());
	            copiedShow.setApprovedBy(show.getApprovedBy());
	            copiedShow.setRgmEmail(show.getRgmEmail());

	            List<ShowTransaction> copiedTxList = new ArrayList<>();
	            if (show.getTransactions() != null) {
	                for (ShowTransaction tx : show.getTransactions()) {
	                    ShowTransaction copiedTx = new ShowTransaction();

	                    // Copy fields
	                    copiedTx.setId(tx.getId());
	                    copiedTx.setOrderId(tx.getOrderId());
	                    copiedTx.setUniqueRequestId(tx.getUniqueRequestId());
	                    copiedTx.setBookingId(tx.getBookingId());
	                    copiedTx.setRrn(tx.getRrn());
	                    copiedTx.setPayauthId(tx.getPayauthId());
	                    copiedTx.setPgId(tx.getPgId());
	                    copiedTx.setPGAmount(tx.getPGAmount());
	                    copiedTx.setPGTransactionId(tx.getPGTransactionId());
	                    copiedTx.setPGAuthId(tx.getPGAuthId());
	                    copiedTx.setSecPgId(tx.getSecPgId());
	                    copiedTx.setSecPgAmount(tx.getSecPgAmount());
	                    copiedTx.setSecPgAuthId(tx.getSecPgAuthId());
	                    copiedTx.setSecPgTransactionId(tx.getSecPgTransactionId());
	                    copiedTx.setBookingStatus(tx.getBookingStatus());
	                    copiedTx.setBookingDateTime(tx.getBookingDateTime());
	                    copiedTx.setMobile(tx.getMobile());
	                    copiedTx.setUsername(tx.getUsername());
	                    copiedTx.setTheaterName(tx.getTheaterName());
	                    copiedTx.setTheaterId(tx.getTheaterId());
	                    copiedTx.setAudi(tx.getAudi());
	                    copiedTx.setSeats(tx.getSeats());
	                    copiedTx.setShowClass(tx.getShowClass());
	                    copiedTx.setShowClassName(tx.getShowClassName());
	                    copiedTx.setFnbCount(tx.getFnbCount());
	                    copiedTx.setFnbTotalPrice(tx.getFnbTotalPrice());
	                    copiedTx.setBalanceAmount(tx.getBalanceAmount());
	                    copiedTx.setConv(tx.getConv());
	                    copiedTx.setDiscount(tx.getDiscount());
	                    copiedTx.setDonation(tx.getDonation());
	                    copiedTx.setDonationType(tx.getDonationType());
	                    copiedTx.setNumOfSeats(tx.getNumOfSeats());
	                    copiedTx.setPassportTicketValue(tx.getPassportTicketValue());
	                    copiedTx.setTicketPrice(tx.getTicketPrice());
	                    copiedTx.setTotalTicketPrice(tx.getTotalTicketPrice());
	                    copiedTx.setPartner(tx.getPartner());
	                    copiedTx.setPaymentStatus(tx.getPaymentStatus());
	                    copiedTx.setPaymode(tx.getPaymode());
	                    copiedTx.setCouponType(tx.getCouponType());
	                    copiedTx.setCancelled(tx.isCancelled());
	                    copiedTx.setRefunded(tx.isRefunded());
	                    copiedTx.setCancelFee(tx.getCancelFee());
	                    copiedTx.setRefundedAmount(tx.getRefundedAmount());
	                    copiedTx.setSapCode(tx.getSapCode());
	                    copiedTx.setRevisedTicketAmount(tx.getRevisedTicketAmount());
	                    copiedTx.setFlexiApplicable(tx.isFlexiApplicable());
	                    copiedTx.setVoucherId(tx.getVoucherId());
	                    copiedTx.setVoucherCode(tx.getVoucherCode());
	                    copiedTx.setRedeemDate(tx.getRedeemDate());

	                    // Set refundable flag
	                 //   copiedTx.setRefundable("INOX DIGITAL".equalsIgnoreCase(tx.getPartner()) && tx.isRefunded());

	                    // Relink
	                    copiedTx.setShow(copiedShow);

	                    copiedTxList.add(copiedTx);
	                }
	            }

	            copiedShow.setTransactions(copiedTxList);
	            showMap.put(movieId, copiedShow);
	        }
	    }

	    return ResponseEntity.ok(new ArrayList<>(showMap.values()));
	}


}
