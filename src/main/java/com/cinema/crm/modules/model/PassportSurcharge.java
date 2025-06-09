package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassportSurcharge {
	private boolean screenUnlock = false;
	private boolean classUnlock = false;
	private boolean filmUnlock = false;
	private boolean blackOutDayUnlock = false;
	private double screenUnlockCharge = 0.0;
	private double classUnlockCharge = 0.0;
	private double filmUnlockCharge = 0.0;
	private double blackOutDayUnlockCharge = 0.0;

}
