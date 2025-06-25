package com.cinema.crm.modules.utils;

import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.modules.entity.Email;
import com.cinema.crm.modules.repository.EmailRepository;

import java.io.File;
import java.util.Date;
import java.util.Properties;

@Log4j2
@Component
public class EmailUtil {

    @Value("${mail.primary.username}")
    private String PRIMARY_USERNAME;
    @Value("${mail.primary.password}")
    private String PRIMARY_PASSWORD;
    @Value("${mail.primary.host}")
    private String PRIMARY_HOST;
    @Value("${mail.primary.port}")
    private int PRIMARY_PORT;

    @Value("${mail.secondary.username}")
    private String SECONDARY_USERNAME;
    @Value("${mail.secondary.password}")
    private String SECONDARY_PASSWORD;
    @Value("${mail.secondary.host}")
    private String SECONDARY_HOST;
    @Value("${mail.secondary.port}")
    private int SECONDARY_PORT;

    @Value("${mail.ternary.username}")
    private String TERNARY_USERNAME;
    @Value("${mail.ternary.password}")
    private String TERNARY_PASSWORD;
    @Value("${mail.ternary.host}")
    private String TERNARY_HOST;
    @Value("${mail.ternary.port}")
    private int TERNARY_PORT;

    @Value("${mail.quarterly.username}")
    private String QUARTERLY_USERNAME;
    @Value("${mail.quarterly.password}")
    private String QUARTERLY_PASSWORD;
    @Value("${mail.quarterly.host}")
    private String QUARTERLY_HOST;
    @Value("${mail.quarterly.port}")
    private int QUARTERLY_PORT;

    @Autowired
	private EmailRepository emailRepository;

    @Async
    public void sendEmail(Email email) {
        log.debug("SENDING EMAIL TO {} :: {}", email.getEmailTo(), email.getType());
        try {
            email.setRetryCount(email.getRetryCount() - 1);
            email.setTimestamp(new Date());
            String response = send(email);
            if (!ObjectUtils.isEmpty(response) && response.equalsIgnoreCase(Result.SUCCESS)) {
                email.setRetryCount(0);
            }
        } catch (final Exception e) {
            email.setRetryCount(email.getRetryCount() - 1);
            log.error("EXCEPTION OCCURED IN SENDING EMAIL TO :: {} :: {}", email.getEmailTo(), e.getMessage());
        }
        emailRepository.save(email);
    }
    public String send(Email email) {
        try {
            JavaMailSenderImpl javaMailSender = null;
            if (email.getEmailFrom().toUpperCase().contains(PRIMARY_USERNAME.toUpperCase())) {
                javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(PRIMARY_HOST);
                javaMailSender.setPort(PRIMARY_PORT);

                javaMailSender.setUsername(PRIMARY_USERNAME);
                javaMailSender.setPassword(PRIMARY_PASSWORD);

                Properties props = javaMailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.socketFactory.port", PRIMARY_PORT);
            } else if (email.getEmailFrom().toUpperCase().contains(SECONDARY_USERNAME.toUpperCase())) {
                javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(SECONDARY_HOST);
                javaMailSender.setPort(SECONDARY_PORT);

                javaMailSender.setUsername(SECONDARY_USERNAME);
                javaMailSender.setPassword(SECONDARY_PASSWORD);

                Properties props = javaMailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.socketFactory.port", SECONDARY_PORT);
            } else if (email.getEmailFrom().toUpperCase().contains(TERNARY_USERNAME.toUpperCase())) {
                javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(TERNARY_HOST);
                javaMailSender.setPort(TERNARY_PORT);

                javaMailSender.setUsername(TERNARY_USERNAME);
                javaMailSender.setPassword(TERNARY_PASSWORD);

                Properties props = javaMailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.socketFactory.port", TERNARY_PORT);
            } else {
                javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(QUARTERLY_HOST);
                javaMailSender.setPort(QUARTERLY_PORT);

                javaMailSender.setUsername(QUARTERLY_USERNAME);
                javaMailSender.setPassword(QUARTERLY_PASSWORD);

                Properties props = javaMailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.socketFactory.port", QUARTERLY_PORT);
            }
            MimeMessage message = javaMailSender.createMimeMessage();
            if (email.getChain().equalsIgnoreCase("PVR")) {
                message.setFrom(new InternetAddress(email.getEmailFrom(), "PVR Cinemas"));
            } else if (email.getChain().equalsIgnoreCase("INOX")) {
                message.setFrom(new InternetAddress(email.getEmailFrom(), "INOX Movies"));
            } else {
                //AURUS CHANGED
                message.setFrom(new InternetAddress(email.getEmailFrom(),"PVR INOX"));
            }

            if (!ObjectUtils.isEmpty(email.getEmailTo())) {
                if (email.getEmailTo().contains(",")) {
                    message.addRecipients(Message.RecipientType.TO, email.getEmailTo());
					/*final String[] arrEmailTO = email.getEmailTo().split(",");
					final Set<String> hashSet = new HashSet<>(Arrays.asList(arrEmailTO));
					for (final String emailTo : hashSet) {
						message.addRecipient(RecipientType.TO, new InternetAddress(emailTo));
					}*/
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getEmailTo()));
                }
            }
            if (!ObjectUtils.isEmpty(email.getEmailCc())) {
                if (email.getEmailCc().contains(",")) {
                    message.addRecipients(Message.RecipientType.CC, email.getEmailCc());
                } else {
                    message.addRecipient(RecipientType.CC, new InternetAddress(email.getEmailCc()));
                }
            }
            if (!ObjectUtils.isEmpty(email.getEmailBcc())) {
                if (email.getEmailBcc().contains(",")) {
                    message.addRecipients(Message.RecipientType.BCC, email.getEmailBcc());
					/*final String[] arrEmailCC = email.getEmailBcc().split(",");
					for (final String emailBcc : arrEmailCC) {
						message.addRecipient(RecipientType.BCC, new InternetAddress(emailBcc));
					}*/
                } else {
                    message.addRecipient(RecipientType.BCC, new InternetAddress(email.getEmailBcc()));
                }
            }
            message.setSubject(email.getSubject());
            message.setHeader("X-Priority", "1");
            message.setContent(email.getBody(), "text/html; charset=utf-8");
          //  message.setText(email.getBody(),"UTF-8" );

            if (!ObjectUtils.isEmpty(email.getAttachmentUrl()) && !ObjectUtils.isEmpty(email.getAttachementFileType())) {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String fileName = (!ObjectUtils.isEmpty(email.getBookingid()) ? email.getBookingid() : System.currentTimeMillis())
                        + email.getAttachmentUrl().substring(email.getAttachmentUrl().lastIndexOf("."), email.getAttachmentUrl().length());
                FileSystemResource file = new FileSystemResource(new File(fileName));
                helper.addAttachment(fileName, file);
            }
            if (email.getFile() != null && email.getFile().exists() && email.getFile().canRead()) {
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
                helper.setText(email.getBody(), true);
                File csvFile = email.getFile();
                if (csvFile.exists() && csvFile.length() > 0) {
                    String fileName = csvFile.getName();
                    helper.addAttachment(fileName, csvFile);
                    log.info("Email sent successfully with attachment: {}", fileName);
                } else {
                    log.error("Attachment file is empty or does not exist.");
                    throw new IllegalArgumentException("Attachment file cannot be empty or nonexistent.");
                }
            }
            javaMailSender.send(message);

            log.debug("MAIL HAS BEEN SENT SUCCESSFULLY TO {} OF TYPE {}", email.getEmailTo(), email.getType());
            return "success";
        } catch (Exception e) {
            log.debug("EXCEPTION OCCURED IN SENDING EMAIL FOR ID :: {} :: {}", email.getId(), e.getMessage());
            return "error";
        }
    }

}
