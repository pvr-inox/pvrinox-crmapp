package com.cinema.crm.databases.pvrinox.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.SBTransactions;


@Repository
public interface SBTransactionRepository extends JpaRepository<SBTransactions, String> {

}