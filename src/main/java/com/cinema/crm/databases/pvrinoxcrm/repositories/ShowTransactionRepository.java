package com.cinema.crm.databases.pvrinoxcrm.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinema.crm.databases.pvrinoxcrm.entities.Show;
import com.cinema.crm.databases.pvrinoxcrm.entities.ShowTransaction;

public interface ShowTransactionRepository extends JpaRepository<ShowTransaction, Long> {
	
	@Query("SELECT DISTINCT s FROM Show s LEFT JOIN FETCH s.transactions")
	List<Show> findAllWithTransactions();

}