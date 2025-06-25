package com.cinema.crm.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuspayCard {

	private String last_four_digits;
	private String card_isin;
	private String expiry_month;
	private String expiry_year;
	private String name_on_card;
	private String card_type;
	private String card_issuer;
	private String card_brand;
	private Boolean saved_to_locker;

	private boolean using_token;
	private boolean using_saved_card;
	private String card_reference;
	private String card_fingerprint;

}