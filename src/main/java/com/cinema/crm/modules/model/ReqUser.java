package com.cinema.crm.modules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUser {
	
	private boolean update;
	private Integer userId;
	private String name;
	private String email;
	private String mobile;
	private String userRole;
	private boolean status;

}
