package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRefundResponse {
    private String transactionId;
    private String bookingStatus;
    private double refundedAmount;
    private LocalDateTime cancelTime;
    private Map<String, Object> refundDetails; // e.g., juspay, gyftr, giftcard
    private Map<String, String> showbizStatus; // e.g., status: SUCCESS, message: ""
}
