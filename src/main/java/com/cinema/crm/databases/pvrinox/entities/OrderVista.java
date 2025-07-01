package com.cinema.crm.databases.pvrinox.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVista {
	
	@Column(columnDefinition = "TEXT") private String vistaException = "";
	@Column(length = 15) private String vistaState = "";
	@Temporal(TemporalType.TIMESTAMP) private Date vistaTime;

}
