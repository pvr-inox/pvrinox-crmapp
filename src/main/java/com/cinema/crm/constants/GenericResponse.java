package com.cinema.crm.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
public class GenericResponse {

	private String result;
	private String responseCode;
	private String message;
}
