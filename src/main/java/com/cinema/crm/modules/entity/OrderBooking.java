package com.cinema.crm.modules.entity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cinema.crm.modules.model.FNBDeliveryObj.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "order_booking")
public class OrderBooking {

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
    @Column(columnDefinition = "varchar(50) default ''") private String source = "";	//SCREENIT //PROMOTION
    
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
    
    @Column(columnDefinition = "varchar(20) default ''") private String userType = "";
    @Column(columnDefinition = "varchar(20) default ''") private String refCode = "";
    
    //AURUS
    @Column(length = 20) private String deliveryMode = "";
    
    @Column(columnDefinition = "json")
    @Convert(converter = DeliveryStatusDataConverter.class)
    private DeliveryStatus fnbDeliveryStatus;
    
    @Column(length = 100, columnDefinition = "varchar(100) default ''") private String deviceId = "";
    
    @Column(columnDefinition = "varchar(100) default ''") private String latitude = "";
    
    @Column(columnDefinition = "varchar(100) default ''") private String longitude = "";
    
    @Column(columnDefinition = "varchar(50) default ''") private String distance = "";

	public long getFinalPrice() {
		return orderTicket.getTotalTicketPrice() 
				+ orderTicket.getConvTotal() 
				+ orderFood.getFbTotalPrice()
				- orderTicket.getDiscount()
				+ orderTicket.getCvpAmount()
				+ orderTicket.getDonation()
				+ orderTicket.getSurchargeAmount()
				+ orderTicket.getFamilyBundleCharge()
				+ orderTicket.getSuperTicketAmount();
	}
	
	public long getTotalPrice() {
		return orderTicket.getTotalTicketPrice() 
				+ orderTicket.getConvTotal() 
				+ orderFood.getFbTotalPrice()
				+ orderTicket.getCvpAmount()
				+ orderTicket.getDonation()
				+ orderTicket.getSurchargeAmount()
				+ orderTicket.getFamilyBundleCharge()
				+ orderTicket.getSuperTicketAmount();
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
    
 // AURUS
    @Converter(autoApply = true)
    public static class DeliveryStatusDataConverter implements AttributeConverter<DeliveryStatus, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(DeliveryStatus attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
            	return null;
            }
        }

        @Override
        public DeliveryStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<DeliveryStatus>() {
                });
            } catch (IOException e) {
            	return null;
            }
        }
    }


    public OrderBooking(OrderCancelled cancelled) {
        this.orderIdEx = cancelled.getOrderIdEx();
        this.transId = cancelled.getTransId();
        this.orderId = cancelled.getOrderId();
        this.foodRefId = cancelled.getFoodRefId();
        this.bookingId = cancelled.getBookingId();
        this.bookType = cancelled.getBookType();
        this.eventType = cancelled.getEventType();
        this.chain = cancelled.getChain();
        this.pos = cancelled.getPos();
        this.mobile = cancelled.getMobile();
        this.email = cancelled.getEmail();
        this.name = cancelled.getName();
        this.userId = cancelled.getUserId();
        this.myTicket = cancelled.getMyTicket();
        this.ipAddress = cancelled.getIpAddress();
        this.platform = cancelled.getPlatform();
        this.platformCancel = cancelled.getPlatformCancel();
        this.appVersion = cancelled.getAppVersion();
        this.qr = cancelled.isQr();
        this.businessDate = cancelled.getBusinessDate();
        this.bookingTime = cancelled.getBookingTime();
        this.cancelTime = cancelled.getCancelTime();
        this.bookingStatus = cancelled.getBookingStatus();
        this.paymentStatus = cancelled.getPaymentStatus();
        this.paymode = cancelled.getPaymode();
        this.paymodeRefund = cancelled.getPaymodeRefund();
        this.dtmSource = cancelled.getDtmSource();
        this.dtmSourcePartner = cancelled.getDtmSourcePartner();
        this.partnerId = cancelled.getPartnerId();
        this.orderFilmCinema = cancelled.getOrderFilmCinema();
        this.orderTicket = cancelled.getOrderTicket();
        this.orderFood = cancelled.getOrderFood();
        this.orderVista = cancelled.getOrderVista();
        this.paymodes = cancelled.getPaymodes();
        this.createdAt = cancelled.getCreatedAt();
        this.modifiedAt = cancelled.getModifiedAt();
        this.eticketUrl = cancelled.getEticketUrl();
        this.dtmCampaign = cancelled.getDtmCampaign();
    }

    public OrderBooking(OrderBooked booked) {
        this.orderIdEx = booked.getOrderIdEx();
        this.transId = booked.getTransId();
        this.orderId = booked.getOrderId();
        this.foodRefId = booked.getFoodRefId();
        this.bookingId = booked.getBookingId();
        this.bookType = booked.getBookType();
        this.eventType = booked.getEventType();
        this.chain = booked.getChain();
        this.pos = booked.getPos();
        this.mobile = booked.getMobile();
        this.email = booked.getEmail();
        this.name = booked.getName();
        this.userId = booked.getUserId();
        this.myTicket = booked.getMyTicket();
        this.ipAddress = booked.getIpAddress();
        this.platform = booked.getPlatform();
        this.platformCancel = booked.getPlatformCancel();
        this.appVersion = booked.getAppVersion();
        this.qr = booked.isQr();
        this.businessDate = booked.getBusinessDate();
        this.bookingTime = booked.getBookingTime();
        this.cancelTime = booked.getCancelTime();
        this.bookingStatus = booked.getBookingStatus();
        this.paymentStatus = booked.getPaymentStatus();
        this.paymode = booked.getPaymode();
        this.paymodeRefund = booked.getPaymodeRefund();
        this.dtmSource = booked.getDtmSource();
        this.dtmSourcePartner = booked.getDtmSourcePartner();
        this.partnerId = booked.getPartnerId();
        this.orderFilmCinema = booked.getOrderFilmCinema();
        this.orderTicket = booked.getOrderTicket();
        this.orderFood = booked.getOrderFood();
        this.orderVista = booked.getOrderVista();
        this.paymodes = booked.getPaymodes();
        this.createdAt = booked.getCreatedAt();
        this.modifiedAt = booked.getModifiedAt();
        this.eticketUrl = booked.getEticketUrl();
        this.dtmCampaign = booked.getDtmCampaign();
    }
}
