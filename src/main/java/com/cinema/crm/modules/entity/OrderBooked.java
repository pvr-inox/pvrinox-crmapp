package com.cinema.crm.modules.entity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_booked")
public class OrderBooked {

    @Id @Column(name = "id", length = 20) private String orderIdEx="";
    private String transId;
    private int orderId;			// Book Id
    @Column(length = 20) private String foodRefId = "";
    @Column(length = 15) private String bookingId = "";		//Kiosk Id
    @Column(length = 15) private String bookType;
    @Column(length = 15) private String eventType;
    @Column(length = 10) private String chain;
    @Column(length = 10) private String pos;
    @Column(length = 15) private String mobile = "";
    @Column(length = 50) private String email = "";
    @Column(length = 150) private String name = "";
    private Long userId;
    private String myTicket = "";
    @Column(length = 50) private String ipAddress = "";
    @Column(length = 20) private String platform = "";
    @Column(length = 20) private String platformCancel = "";
    @Column(length = 20) private String appVersion = "";

    private boolean qr;

    @Temporal (TemporalType.DATE) private Date businessDate;
    @Temporal (TemporalType.TIMESTAMP) private Date bookingTime;
    @Temporal (TemporalType.TIMESTAMP) private Date cancelTime;

    @Column(length = 25) private String bookingStatus = "";
    @Column(length = 25) private String paymentStatus = "";
    @Column(length = 60) private String paymode;
    @Column(length = 60) private String paymodeRefund;

    @Column(length = 50) private String dtmSource = "";
    @Column(length = 50) private String dtmSourcePartner = "";
    @Column(length = 50) private String partnerId = "";
    
    @Column(columnDefinition = "varchar(50) default ''") private String showOrderId = "";
    @Column(columnDefinition = "varchar(50) default ''") private String source = "";	//SCREENIT

    @Embedded private OrderFilmCinema orderFilmCinema = new OrderFilmCinema();
    @Embedded private OrderTicket orderTicket = new OrderTicket();
    @Embedded private OrderFood orderFood = new OrderFood();
    @Embedded private OrderVista orderVista = new OrderVista();
    @Embedded private Paymodes paymodes = new Paymodes();

    @CreationTimestamp @JsonIgnore @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;
    @UpdateTimestamp @JsonIgnore @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date modifiedAt;

    private String eticketUrl = "";

    @Column(length = 1000) private String dtmCampaign = "";
    
    @Column(columnDefinition = "varchar(20) default ''") private String refCode = "";

    public long getFinalPrice() {
        return orderTicket.getTotalTicketPrice()
                + orderTicket.getConvTotal()
                + orderFood.getFbTotalPrice()
                - orderTicket.getDiscount()
                + orderTicket.getCvpAmount()
                + orderTicket.getDonation()
                + orderTicket.getSurchargeAmount()
                + orderTicket.getFamilyBundleCharge();
    }

    public long getTotalPrice() {
        return orderTicket.getTotalTicketPrice()
                + orderTicket.getConvTotal()
                + orderFood.getFbTotalPrice()
                + orderTicket.getCvpAmount()
                + orderTicket.getDonation()
                + orderTicket.getSurchargeAmount()
                + orderTicket.getFamilyBundleCharge();
    }

    //Item Tab Convertor
    @Converter(autoApply = true)
    public static class FoodConverter implements AttributeConverter<List<Food>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<Food> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON Foods to String", e);
            }
        }

        @Override
        public List<Food> convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<List<Food>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String Foods to JSON", e);
            }
        }
    }
}
