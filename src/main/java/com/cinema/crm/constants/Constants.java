package com.cinema.crm.constants;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final String INITIATE = "initiate refund";

	public static class RespCode {
		public static final String SUCCESS = "00";
		public static final String FAILED = "100";
		public static final String PENDING = "101";
		public static final String NO_DATA_FOUND = "102";
	}

	public static class Result {
		public static final String SUCCESS = "success";
		public static final String FAILED = "failed";
		public static final String ERROR = "error";
		public static final String PENDING = "pending";
	}
	
	public static class Message {
		public static final String SUCCESS = "success";
		public static final String BOOKING_NOT_FOUNDED = "Refund cannot be initiated as no booking was found with the associated booking ID.";
		public static final String REQUESTED_NODAL_OFFICER = "Refund request has been sent to Nodal Officer.";
	}
}
