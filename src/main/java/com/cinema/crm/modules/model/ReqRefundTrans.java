package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqRefundTrans {
    private String ccode;
    private String platform;
    private String chain;
    private String bookId;
    private String cardNumber;
    private String customerName;
    
    //For ShowBiz
    private String receiptNo;
    private String remarks;
    private String showDate;
    private String showTime;
    private int screenId;
    private String rate;
    private int noOfTicket;
    private String value;
    private int fbNoOfItems;
    private String fbValue;
    private String pgId;
    private String transType;
    private int cancelBy = 1;
}