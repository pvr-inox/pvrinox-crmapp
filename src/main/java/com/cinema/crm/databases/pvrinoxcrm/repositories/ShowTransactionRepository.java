package com.cinema.crm.databases.pvrinoxcrm.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.crm.databases.pvrinoxcrm.entities.ShowTransaction;

public interface ShowTransactionRepository extends JpaRepository<ShowTransaction, Long> {
}