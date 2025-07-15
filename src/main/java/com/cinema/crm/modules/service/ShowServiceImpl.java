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
}
