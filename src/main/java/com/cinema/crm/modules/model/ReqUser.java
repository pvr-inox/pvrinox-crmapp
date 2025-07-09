package com.cinema.crm.modules.model;

import jakarta.validation.constraints.Pattern;
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
	private String mobile;
	private String userRole;
	private boolean status;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, digit, and special character.")
	private String password;

}
