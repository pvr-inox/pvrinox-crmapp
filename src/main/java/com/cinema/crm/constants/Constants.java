package com.cinema.crm.constants;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final String REFUND_INITIATE = "REFUND_INITIATED";
	public static final String REFUND_COMPLETED = "REFUND_COMPLETED";
	public static final String ROLLEDBACK = "ROLLEDBACK";
	public static final String RGM_CANCEL = "NODAL_OFFICER_CANCEL";
	public static final String CANCEL_COMPLETE = "CANCEL_COMPLETE";
	public static final String CRM_ROLLEDBACK = "CRM_ROLLEDBACK";
	public static final String CANCEL = "CANCEL";
	public static final String REDEEMED = "REDEEMED";
	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "REJECTED";


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

	public static class UserRoles {
		public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
		public static final String EXECUTIVE = "ROLE_CRM_EXECUTIVE";
		public static final String OFFICER = "ROLE_NODAL_OFFICER";
	}
	public static class Message {
		public static final String SUCCESS = "success";
		public static final String BOOKING_NOT_FOUNDED = "Refund cannot be initiated as no booking was found with the associated booking ID.";
		public static final String REQUESTED_NODAL_OFFICER = "Refund request has been sent to Nodal Officer.";
		public static final String ALREADY_REQUESTED_NODAL_OFFICER = "A refund has already been sent to nodal officer for this order with this booking id.";
		public static final String ALREADY_PROCESSED = "A refund has already been processed for this order with this unique booking id.";
		public static final String REFUND_REQUEST_RAISED = "Refund request was raised for this transaction.";
		public static final String USER_ALREADY_EXIST = "The user is already exist.";
		public static final String USER_REGISTERED_SUCCESSFULLY = "User registration successful.";
		public static final String USER_REGISTRATION_FAILED = "Failed to register.";
		public static final String FAILED_TO_LOGGED_IN = "Failed to login.";
		public static final String REFUND_REQUEST_ERROR = "Failed to initiate refund.";
		public static final String REFUND_REQUEST_REJECTED = "Refund request rejected.";
	}
	
	public static class ShowBizTransType {
		public static final String Normal = "Normal";
		public static final String InCinemaFB = "InCinemaFB";
		public static final String OnlyFB = "OnlyFB";
		public static final String IssueVoucher = "IssueVoucher";
	}

	public static class Foodtype {
		public static final String NORMAL = "NORMAL";
		public static final String ONSEAT = "ONSEAT";
		public static final String ADDFOOD = "ADDFOOD";
		public static final String ONLYFOOD = "ONLYFOOD";
	}
	
	public static class TransType {
    	public static final String BOOKING = "BOOKING";
    	public static final String BOOKED = "BOOKED";
    	public static final String FOOD = "FOOD";
    }
	
	 public static class ORDER_TYPE {
	        public static final String booking = "BOOKING";
	        public static final String food = "FOOD";
	        public static final String giftcard = "GIFTCARD";
	        public static final String passport = "PASSPORT";
	        public static final String show = "SHOWBOOK";
		}
}
