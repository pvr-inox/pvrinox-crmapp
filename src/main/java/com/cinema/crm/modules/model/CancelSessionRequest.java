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
public class CancelSessionRequest {
    private Long sessionId;
    private String refundType; // "f" = full, "p" = partial
    private List<String> transactionIds; // used only when refundType = "p"
}
