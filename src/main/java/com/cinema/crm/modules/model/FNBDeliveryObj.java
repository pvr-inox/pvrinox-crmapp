package com.cinema.crm.modules.model;

import java.util.List;
import java.util.Map;

import com.cinema.crm.databases.pvrinox.entities.Food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class FNBDeliveryObj {
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FNBDeliveryStatusObj {
		private DeliveryStatus deliveryStatus;
		private OtherDetails otherDetails;
		private Map<String,String> showbizConfigStatus;
	}
	
	@Getter
	@Setter
	public static class DeliveryStatus {
		
		    private String orderStatus;
		    private String orderTakeOn;
		    private String startedOn;
		    private String readyOn;
		    private String deliveredOn;
		   // private String deliveryCode;
		    private Double totalCharge;
		    private String deliveryMode;
		   // private String deliveryTimeMode;
		    private String deliveryTime;
		   // private String itemGSTInvoiceNo;
		    private long autoRefreshTime;
		    private String statusEndTime;
		    private String itemReceiptNo;
		    private Integer uniqueRequestId;
		    private Integer preparationTime;

	}
	
	@Getter
	@Setter
	public static class OtherDetails {
		private String bookingId;       // booking_id
	    private String name;            // Name
	    private String filmName;        // film_name
	    private String theaterName;     // theater_name
	    private long fbTotalPrice;    // fb_total_price
	    private String audi;            // audi
	    private String seats;           // seats
	    private String showTime;        // show_time
	    private String createdAt;        // createdAt
	    private String deliveryMode;        // delivery_mode
	    //private List<HoldInCinema> fnbFoods;        // fnb_foods
		private List<Food> Foods;
	    private String payMode;         // paymode
	    private String theaterId;
	    private String deliveryStatus;
	    private String bookingStatus;
	    private long discount;    // fb_discount
	    private String charge;
	    private String cGST;
	    private String sGST;
	    private String genre;
	    private String language;
	    private String filmId;

	}
	
	

}
