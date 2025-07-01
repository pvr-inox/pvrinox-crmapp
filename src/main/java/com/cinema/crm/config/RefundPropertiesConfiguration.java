package com.cinema.crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "refund")
public class RefundPropertiesConfiguration {
	private long timeout;
	private String juspayMid;
    private String juspayClientId;
    private String juspayUrl;
    private String juspayKey;
    private String juspayUsername;
    private String juspayPassword;
    private String giftcardUrl;
    private String giftcardTerminalid;
    private String giftcardUsername;
    private String giftcardPassword;
    private long giftcardTransactionid;
    private String gyftrUserid;
    private String gyftrPassword;
    private String gyftrUrl;
    private String gyftrShopcode;
}
