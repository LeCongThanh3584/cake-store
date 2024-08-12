package com.example.demo.admin.service;

import javax.mail.MessagingException;

public interface MailService {

    void sendEmail(String toEmail, String token) throws MessagingException;
}
