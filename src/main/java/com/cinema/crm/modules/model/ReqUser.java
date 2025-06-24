package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUser {
	
	private boolean update;
	private Integer userId;
	private String userFirstName;
	private String userLastName;
	private String email;
	private Integer mobile;
	private String userRole;
	private boolean status;

}
