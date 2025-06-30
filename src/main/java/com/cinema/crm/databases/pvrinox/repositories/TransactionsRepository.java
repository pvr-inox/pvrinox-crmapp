package com.cinema.crm.databases.pvrinox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String>{

}
