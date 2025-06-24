package com.cinema.crm.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
public class InitiateRefundResponse extends GenericResponse{
	private String bookingId;
}
