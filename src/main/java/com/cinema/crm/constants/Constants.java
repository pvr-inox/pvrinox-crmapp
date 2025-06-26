package com.cinema.crm.constants;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final String REFUND_INITIATE = "REFUND_INITIATED";
	public static final String REFUND_COMPLETED = "REFUND_COMPLETED";
	public static final String ROLLEDBACK = "ROLLEDBACK";
	public static final String RGM_CANCEL = "RGM_CANCEL";
	public static final String CRM_CANCEL = "CRM_CANCEL";
	public static final String CRM_ROLLEDBACK = "CRM_ROLLEDBACK";

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
		public static final String ALREADY_REQUESTED_NODAL_OFFICER = "A refund has already been sent to nodal officer for this order with this booking id.";
		public static final String ALREADY_PROCESSED = "A refund has already been processed for this order with this unique booking id.";
		public static final String REFUND_REQUEST_RAISED = "Refund request was raised for this transaction.";
	}
}
