package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketInvoiceData {
	
	private String ticketNo;
    private String rowNo;
    private String seatNo;
    private String seats;
    private String rate;
    private String gstInvoiceNo;
    private String ticketPrice;
    private String cGST;
    private String sGST;
    private String gstCNNo;
    private String qr;
    private String status;
    private String tu;

}
