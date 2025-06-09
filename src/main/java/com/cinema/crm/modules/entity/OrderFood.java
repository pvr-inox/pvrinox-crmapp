package com.cinema.crm.modules.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import com.cinema.crm.modules.model.RupeeFormatSerializer;
import com.cinema.crm.modules.entity.OrderBooking.FoodConverter;

@Getter
@Setter
@Embeddable
public class OrderFood {

	private boolean fnb = false;
	@JsonSerialize(using = RupeeFormatSerializer.class)
	private long fbTotalPrice = 0;
	// private String foods = "";// desc|id|quantity|price#desc|id|quantity|price
	@Column(columnDefinition = "json")
	@Convert(converter = FoodConverter.class)
	List<Food> foods = new ArrayList<>();
	@Column(length = 20)
	private String pickUpTime = "";
	private int fbCount = 0;
	@JsonSerialize(using = RupeeFormatSerializer.class)
	private long fbDiscount = 0;
	@Column(length = 12)
	private String foodType="";
}
