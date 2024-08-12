package com.example.demo.admin.schedule;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

@WebListener
public class LoginListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String username = (String) session.getAttribute("username");
        Integer role = (Integer) session.getAttribute("role");
        Integer storeId = (Integer) session.getAttribute("storeId");

        if (username != null && role != null && storeId != null) {
            new Thread(new NotificationSchedule(session, role, storeId)).start();
        }
        HttpSessionAttributeListener.super.attributeAdded(event);
    }
}
