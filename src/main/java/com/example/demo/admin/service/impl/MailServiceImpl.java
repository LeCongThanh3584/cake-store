package com.example.demo.admin.service.impl;

import com.example.demo.admin.service.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailServiceImpl implements MailService {

    @Override
    public void sendEmail(String toEmail, String token) throws MessagingException {
        String username = "qtranvuminh@gmail.com";
        String password = "wgow iavo yuzs qhng";
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Reset Password");
        message.setContent("Click <a href=\"" + resetLink + "\">here</a> to reset your password.", "text/html");

        Transport.send(message);
    }
}
