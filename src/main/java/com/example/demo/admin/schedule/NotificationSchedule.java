package com.example.demo.admin.schedule;

import com.example.demo.admin.dao.CakeStoreDao;
import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.util.Constant.ROLE;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class NotificationSchedule implements Runnable{
    private CakeStoreDao cakeStoreDao = new CakeStoreDao();
    private HttpSession session;
    private Integer role;
    private Integer storeId;

    public NotificationSchedule(HttpSession session, Integer role, Integer storeId) {
        this.session = session;
        this.role = role;
        this.storeId = storeId;
    }

    @Override
    public void run() {
        List<CakeStoreResponse> updatedList = new ArrayList<>();
        if(role.equals(ROLE.SUPER_ADMIN)){
             updatedList = cakeStoreDao.getAllCakeByExpritedDate(0);
        } else if (role.equals(ROLE.ADMIN)){
             updatedList = cakeStoreDao.getAllCakeByExpritedDate(storeId);
        }
        session.setAttribute("expiringProducts", updatedList);
    }
}
