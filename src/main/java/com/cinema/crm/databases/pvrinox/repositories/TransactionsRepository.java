package com.cinema.crm.databases.pvrinox.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String>{
	List<Transactions> findAllBySessionId(Long sessionId);

}
