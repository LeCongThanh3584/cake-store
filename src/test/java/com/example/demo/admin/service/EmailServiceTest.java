package com.example.demo.admin.service;

import com.example.demo.admin.service.impl.MailServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceTest {
    @InjectMocks
    private MailServiceImpl mailServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() throws Exception {
        String toEmail = "recipient@example.com";
        String token = "dummyToken";
        Properties properties = Mockito.mock(Properties.class);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("qtranvuminh@gmail.com","3256185981589157");
            }
        };
        //Session session = Session.getInstance(properties, authenticator);

        //MimeMessage mimeMessage = new MimeMessage(session);

        mailServiceImpl.sendEmail(toEmail, token);

        Mockito.verify(mailServiceImpl,Mockito.times(1)).sendEmail(toEmail, token);
    }
}
