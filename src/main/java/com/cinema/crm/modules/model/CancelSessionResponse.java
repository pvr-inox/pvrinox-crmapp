package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelSessionResponse {
    private List<TransactionRefundResponse> refundResponses;
    private int totalTransactions;
    private int successfullyRefunded;
    private int failedRefunds;
    private boolean partialRefund;
    private boolean fullyRefunded;
}
