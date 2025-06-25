package com.cinema.crm.modules.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "shows")
public class Show {

    @Id
    private Long movieId;

    private String movieName;
    private String certificate;
    private String format;
    private String genre;
    private String language;
    private Long screenId;
    private String screenName;
    private String sessionId;
    private LocalDateTime showDateTime;
    private Long theatreId;
    private Integer soldSeat;
    private Integer totalSeat;
    private Integer cancelSeat;
    private LocalDateTime showCancelDateTime;
    private String cancelationResone;
    private String approvedBy;
    private String rgmEmail;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowTransaction> transactions;
}