package com.cinema.crm.modules.entity;

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
	public String bookingId;
    public String eventName;
    public String customerName;
    public String refundType;
    @Column(name = "refund_status")
    public String refundStatus;
    public String refundReasons;
    public String totalAmount;
    public String refundAmount;
    public String refund;
    public String paymentGateway;
    public String remarks;

}
