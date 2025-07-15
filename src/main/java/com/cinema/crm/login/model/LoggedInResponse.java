package com.cinema.crm.login.model;

import java.util.Date;

import com.cinema.crm.constants.GenericResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class LoggedInResponse extends GenericResponse {

	private String username;
	private String email;
	private String mobile;
	private String profile;
	private String modules;
	private String token;
	private Date issuedAt;
	private Date expiration;
}
