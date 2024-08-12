package com.example.demo.client.service.impl;

import com.example.demo.client.dao.AddressDao;
import com.example.demo.client.service.AddressService;
import com.example.demo.entity.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {

    private static final String provinceURL = "https://esgoo.net/api-tinhthanh/1/0.htm";

    private static final String districtURL = "https://esgoo.net/api-tinhthanh/2/";

    private static final String wardsURL = "https://esgoo.net/api-tinhthanh/3/";

    AddressDao addressDao = new AddressDao();

    @Override
    public List<Address> getAddresses(int userId) {
        return addressDao.getAddresses(userId);
    }

    @Override
    public Address getAddress(int userId, Integer addressId) {
        return addressDao.getAddress(userId, addressId);
    }

    @Override
    public List<Address> getAddressByUserId(int page, int pageSize, int userId, String search) {
        return addressDao.getAllAddressByUserId(page, pageSize, userId, search);
    }

    @Override
    public Address getAddressById(long id) {
        return addressDao.getAddressById(id);
    }

    @Override
    public boolean createAddress(Address address) {
        return addressDao.createAddress(address);
    }

    @Override
    public boolean updateAddress(Address address) {
        return addressDao.updateAddress(address);
    }

    @Override
    public int getTotalRecord(int userId, String search) {
        return addressDao.getTotalRecords(userId, search);
    }

    @Override
    public Map<String, String> validateCreate(HttpServletRequest request) throws IOException {
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "name is required");
        }

        String phone = request.getParameter("phone");
        if (!phone.matches("[0-9]{10}+")) {
            errors.put("phone", "phone must have leatse 10 number long");
        }

        return errors;
    }

    @Override
    public Map<String, String> validateUpdate(HttpServletRequest request) throws IOException {
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("cakeName");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "name is required");
        }

        return errors;
    }

    @Override
    public Map<String, Object> getProvince() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(provinceURL);
            String result = EntityUtils.toString(httpClient.execute(request).getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDistrict(String idProvince) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(districtURL + idProvince + ".htm");
            String result = EntityUtils.toString(httpClient.execute(request).getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getWards(String idDistrict) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(wardsURL + idDistrict + ".htm");
            String result = EntityUtils.toString(httpClient.execute(request).getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
