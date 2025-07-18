package com.cinema.crm.modules.service;


import org.springframework.http.ResponseEntity;

import com.cinema.crm.databases.pvrinoxcrm.entities.Show;

public interface ShowService {
    ResponseEntity<Object> saveShow(Show show);
    
    ResponseEntity<Object> getAllShowsWithRefundableFlag();
}
