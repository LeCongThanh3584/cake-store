package com.example.demo.client.service.impl;

import com.example.demo.client.dao.CartDao;
import com.example.demo.client.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MailServiceImpl implements MailService {

    private static final long serialVersionUID = 1L;

    private Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);

    @Override
    public void sendOtpMail(String reciever, String otp) {
        freemarkerConfig.setClassForTemplateLoading(getClass(), "/templates");
        try {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("otp", otp);
            templateData.put("name", reciever);

            Template template = freemarkerConfig.getTemplate("otp-template.ftl");
            StringWriter writer = new StringWriter();
            template.process(templateData, writer);
            String emailContent = writer.toString();

            sendEmail(reciever, "OTP for Verification", emailContent);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }

    }

    private void sendEmail(String toEmail, String subject, String emailContent) throws MessagingException {
        final String username = "sonptph25875@fpt.edu.vn";
        final String password = "mbwa qgvk base rslk";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(emailContent, "text/html");

        Transport.send(message);
    }

}
