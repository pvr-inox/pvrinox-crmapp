package com.cinema.crm.modules.entity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.cinema.crm.modules.model.PassportSurcharge;
import com.cinema.crm.modules.model.RupeeFormatSerializer;
import com.cinema.crm.modules.model.TicketInvoiceData;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class OrderTicket {
	@Column(length = 100)
    private String seats = "";
    @Column(length = 25)
    private String audi = "";
    private int screenId = 0;
    private long sessionId = 0;
    @Column(length = 50)
    private String showClass = "";
    private String showClassName = "";
    @Column(length = 15)
    private String pgrpId = "";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, MMM dd, hh:mm a", timezone = "Asia/Kolkata")
    private Date showTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, MMM dd, hh:mm a", timezone = "Asia/Kolkata")
    private Date endTime;
    @Column(columnDefinition = "TINYINT default 0")
    private boolean cvp = false;
    @Column(columnDefinition = "varchar(500) default ''")
    private String cvpInfo = "";
    @Column(columnDefinition = "BIGINT default 0")
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long cvpAmount = 0;
    @Column(columnDefinition = "varchar(500) default ''")
    private String cvpVouchers = "";
	@Column(columnDefinition = "TINYINT default 0")
	private boolean familyBundle = false;
	@Column(columnDefinition = "varchar(500) default ''")
	private String familyBundleInfo = "";
	@Column(columnDefinition = "BIGINT default 0")
	private long familyBundleAmount = 0;
	@Column(columnDefinition = "BIGINT default 0")
	private long familyBundleCharge = 0;
	
	private int numOfSeats = 0;
    private int adultSeats = 0;
    private int childSeats = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long ticketPrice = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long childTicketPrice = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long totalTicketPrice = 0;
    private long ticketTax = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long conv = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long convGst = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long convTotal = 0;
    @Column(columnDefinition = "BIGINT default 0")
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long rateCard = 0;
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long discount = 0;
    @Column(length = 30)
    private String donationType = "";
    @Column(length = 250)
    private String gstInvoiceNo = "";
    @Column(length = 250)
    private String gstInvoiceNoCN = "";
    private String gstInvoiceNoCAN = "";
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long donation = 0;
    @Column(columnDefinition = "varchar(50) default '0'")
    private String balanceAmount = "0";
    private long refundedAmount;
    private long cancelFee;
    @Column(columnDefinition = "TINYINT default 0")
    private boolean additionalLangClip;

    @Column(columnDefinition = "TINYINT default 0")
    private boolean flexiApplicable = false;

    @Column(columnDefinition = "TINYINT default 0")
    private boolean flexiApply = false;

    @Column(columnDefinition = "BIGINT default 0")
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long flexiTicketPrice = 0;
    //
//    @Column(columnDefinition = "BIGINT default 0")
    @Transient
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long flexiTotalTicketPrice = 0;


    @Column(columnDefinition = "BIGINT default 0")
    private long passportTicketValue = 0;
    @Column(length = 30)
    private String experience;

    @JsonSerialize(using = RupeeFormatSerializer.class)
    @Column(columnDefinition = "BIGINT default 0")
    private long surchargeAmount = 0;

    @Column(columnDefinition = "json")
    @Convert(converter = TicketInvoiceDataConverter.class)
    private List<TicketInvoiceData> ticketInvoiceData;

    @Column(columnDefinition = "TEXT")
    private String orderData = "";

    @JsonSerialize(using = RupeeFormatSerializer.class)
    @Column(columnDefinition = "BIGINT default 0")
    private long revisedTicketAmount = 0;
    
    @Column(columnDefinition = "INT default 0")
    private long ticketsRefunded = 0;
    
    @Column(columnDefinition = "json")
    @Convert(converter = SurchargeConverter.class)
    private PassportSurcharge surcharge;
    
    //@Column(columnDefinition = "BIGINT default 0")
    //@JsonSerialize(using = RupeeFormatSerializer.class)
   // private long flexiRefundedAmount = 0;
    
    @Column(columnDefinition = "TINYINT default 0")
    private boolean superTicket = false;
    
    @Column(columnDefinition = "BIGINT default 0")
    @JsonSerialize(using = RupeeFormatSerializer.class)
    private long superTicketAmount = 0;
    
    @Column(columnDefinition = "varchar(2000) default ''")
    private String superTicketVouchers = "";
    
    @Converter(autoApply = true)
    public static class SurchargeConverter implements AttributeConverter<PassportSurcharge, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(PassportSurcharge attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON Surchase to String", e);
            }
        }

        @Override
        public PassportSurcharge convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<PassportSurcharge>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String Surcharge to JSON", e);
            }
        }
    }
    
    @Converter(autoApply = true)
    public static class TicketInvoiceDataConverter implements AttributeConverter<List<TicketInvoiceData>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<TicketInvoiceData> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON Surchase to String", e);
            }
        }

        @Override
        public List<TicketInvoiceData> convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<List<TicketInvoiceData>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String Surcharge to JSON", e);
            }
        }
    }
   
}
