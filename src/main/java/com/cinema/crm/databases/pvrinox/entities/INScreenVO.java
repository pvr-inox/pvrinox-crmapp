package com.cinema.crm.databases.pvrinox.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class INScreenVO {
    private String id;
    private boolean handicap = false;
    private boolean campanion = false;
    private List<INAreaVO> areas;

    @Getter
    @Setter
    public static class INAreaVO {
        private String name;
        private String code;
        private int priority = 0;
        private double ticketRate;
        private double ThreeDRatePerTicket;
        private double convFee;
        private double cgst;
        private double sgst;
        private List<INRowVO> rows = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class INRowVO {
        private String name;
        private List<INSeatVO> seats = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class INSeatVO {
        private String seatNumber = "0";
        private String seatType = "";//NORMAL,WHEEL,COMPANION
        private boolean status = true; //true: actual, false: blank
        private boolean seatStatus = true; //true: actual, false: blank
    }
}