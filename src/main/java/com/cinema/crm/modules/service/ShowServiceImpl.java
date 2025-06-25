package com.cinema.crm.modules.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinema.crm.modules.model.Show;
import com.cinema.crm.modules.model.ShowTransaction;
import com.cinema.crm.modules.repository.ShowRepository;
import com.cinema.crm.modules.repository.ShowTransactionRepository;

@Service
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;

	public ShowServiceImpl(ShowRepository showRepository, ShowTransactionRepository transactionRepository) {
		this.showRepository = showRepository;
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
