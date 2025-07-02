package com.cinema.crm.modules.service;

import java.util.HashMap;
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
	public ResponseEntity<Object> saveShow(Show show) {
		if (show.getTransactions() != null) {
			for (ShowTransaction transaction : show.getTransactions()) {
				transaction.setShow(show);
			}
		}

		showRepository.save(show);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Show and Transactions saved successfully.");
		response.put("data", show);
		return ResponseEntity.ok(response);
	}

}
