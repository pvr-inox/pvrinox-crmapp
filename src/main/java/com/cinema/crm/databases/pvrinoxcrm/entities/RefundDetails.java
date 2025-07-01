package com.cinema.crm.databases.pvrinoxcrm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "refund_details")
public class RefundDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "booking_id") private String bookingId;
	@Column(name = "event_name") private String eventName;
	@Column(name = "customer_name") private String customerName;
	@Column(name = "refund_type") private String refundType;
    @Column(name = "refund_status") private String refundStatus;
    @Column(name = "refund_reasons") private String refundReasons;
    @Column(name = "total_amount") private String totalAmount;
    @Column(name = "refund_amount") private String refundAmount;
    @Column(name = "refund") private String refund;
    @Column(name = "payment_gateway") private String paymentGateway;
    @Column(name = "remarks") private String remarks;

}
