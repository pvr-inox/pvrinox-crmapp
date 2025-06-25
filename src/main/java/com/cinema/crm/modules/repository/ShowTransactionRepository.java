package com.cinema.crm.modules.repository;


import com.cinema.crm.modules.model.ShowTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowTransactionRepository extends JpaRepository<ShowTransaction, Long> {
}