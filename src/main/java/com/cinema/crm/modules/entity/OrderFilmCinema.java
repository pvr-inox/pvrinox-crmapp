package com.cinema.crm.modules.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class OrderFilmCinema {
	@Column(length = 20) private String filmMasterId = "";
	@Column(length = 20) private String filmId = "";
	@Column(length = 100) private String filmName = "";
	@Column(length = 5) private String certificate = "";
	@Column(length = 40) private String genre = "";
	@Column(length = 40) private String language = "";
	@Column(length = 15) private String format = "";
	@Column(length = 20) private String theaterId = "";
	@Column(length = 100) private String theaterName = "";
	@Column(length = 20) private String stateGSTN = "";
	@Column(length = 20) private String hsnSacCode="";
	@Column(length = 20) private String cityName = "";
	private boolean cancelAvail;
	@Transient private String posterHori = "";
	@Transient private String posterVert = "";
	@Column(length = 20, columnDefinition = "varchar(100) default ''") private String sapCode;
	@Column(length = 20) private String posHoCode;
}
