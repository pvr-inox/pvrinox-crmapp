package com.cinema.crm.modules.transactions;

import com.cinema.crm.databases.pvrinoxcrm.entities.Show;
import com.cinema.crm.modules.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/show")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveShowWithTransactions(@RequestBody Show show) {
        return showService.saveShow(show);
    }
    
    @GetMapping("/cancelledshow")
    public ResponseEntity<Object> getAllShowsWithRefundables() {
        return showService.getAllShowsWithRefundableFlag();
    }
}
