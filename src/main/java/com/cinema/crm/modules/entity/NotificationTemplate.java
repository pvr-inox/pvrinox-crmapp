package com.cinema.crm.modules.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_template")
public class NotificationTemplate {

    @Id
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "chain", length = 20)
    private String chain;

    @Column(name = "content_id", length = 100)
    private String contentId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "email_active", nullable = false)
    private boolean emailActive;

    @Column(name = "email_bcc", length = 255)
    private String emailBcc;

    @Column(name = "email_cc", length = 255)
    private String emailCc;

    @Column(name = "email_from", length = 100)
    private String emailFrom;

    @Column(name = "email_subject", length = 200)
    private String emailSubject;

    @Column(name = "email_template", columnDefinition = "TEXT")
    private String emailTemplate;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "sms_active", nullable = false)
    private boolean smsActive;

    @Column(name = "sms_template", columnDefinition = "TEXT")
    private String smsTemplate;

    @Column(name = "whatsapp_active", nullable = false)
    private boolean whatsappActive;

    @Column(name = "whatsapp_template", columnDefinition = "TEXT")
    private String whatsappTemplate;

    @Column(name = "email_save")
    private Boolean emailSave;

    @Column(name = "sms_save")
    private Boolean smsSave;

}
