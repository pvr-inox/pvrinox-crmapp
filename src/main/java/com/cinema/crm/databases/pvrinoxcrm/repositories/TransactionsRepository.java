package com.cinema.crm.databases.pvrinoxcrm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinoxcrm.entities.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {

	Optional<Transactions> findById(String id);
	
}
