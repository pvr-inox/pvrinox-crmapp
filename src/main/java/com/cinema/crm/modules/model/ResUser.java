package com.cinema.crm.modules.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResUser {
	
	private Integer userId;
	private String name;
	private String email;
	private String mobile;
	private String role;
	@JsonIgnore
	private String password;
	private Boolean status;
	private Timestamp createdAt;
	private Timestamp modifiedAt;

}
