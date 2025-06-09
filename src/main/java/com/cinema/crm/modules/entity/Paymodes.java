package com.cinema.crm.modules.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Paymodes {

	private boolean couponused = false;
	private boolean juspayused = false;
	private long juspayamt = 0;
	private boolean giftcardused = false;
	private boolean dccardused = false;
	private boolean gyfterused = false;
	private boolean hyattused = false;
	private boolean razorpayused = false;
	@Column(columnDefinition = "TINYINT default 0") private boolean instapayused = false;
	@Column(length = 20) private String pgType = "";
	@Column(length = 20) private String couponType = "";
	
	

}
