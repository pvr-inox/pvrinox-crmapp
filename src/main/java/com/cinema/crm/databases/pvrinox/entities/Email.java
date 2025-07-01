package com.cinema.crm.databases.pvrinox.entities;

import java.io.File;
import java.util.Date;

import org.springframework.core.io.ByteArrayResource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emails")
public class Email {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
	@Column(columnDefinition = "TEXT") private String body;
	private String subject = "";
	private String emailFrom = "";
	private String emailTo = "";
	private String emailCc = "";
	private String emailBcc = "";
	private String attachmentUrl;
	@Transient
	private ByteArrayResource attachmentFile;
	private String attachementFileType;
	@Temporal(TemporalType.TIMESTAMP) private Date timestamp;
	private int retryCount;
	private String type = "";
	private String bookingid = "";
	@Column(length = 30) private String chain;
	@Transient private boolean emailSave = false;
	@Transient private File file;
	
	public Email(String body, String subject, String emailFrom, String emailTo, String emailCc, String emailBcc, Date timestamp, 
			int retryCount, String type, String bookingid, String chain) {
		super();
		this.body = body;
		this.subject = subject;
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailCc = emailCc;
		this.emailBcc = emailBcc;
		this.timestamp = timestamp;
		this.retryCount = retryCount;
		this.type = type;
		this.bookingid = bookingid;
		this.chain = chain;
	}

	public Email(String body, String subject, String emailFrom, String emailTo, String emailCc, String emailBcc, Date timestamp,
				 int retryCount, String type, String bookingid, String chain, boolean emailSave) {
		super();
		this.body = body;
		this.subject = subject;
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.emailCc = emailCc;
		this.emailBcc = emailBcc;
		this.timestamp = timestamp;
		this.retryCount = retryCount;
		this.type = type;
		this.bookingid = bookingid;
		this.chain = chain;
		this.emailSave = emailSave;
	}

}
