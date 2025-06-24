package com.cinema.crm.modules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.modules.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {

	Optional<Transactions> findById(String id);
	
}
