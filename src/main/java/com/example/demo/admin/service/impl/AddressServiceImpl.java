package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.AddressDao;
import com.example.demo.admin.dao.UserDao;
import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.AddressService;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao;

    private UserDao userDao;

    public AddressServiceImpl() {
        addressDao = new AddressDao();
        userDao = new UserDao();
    }

    @Override
    public Address getAddressById(Integer idAddress) {
        return addressDao.getAddressById(idAddress);
    }

    @Override
    public Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId) {
        UserDto existUser = userDao.getUserById(userId);
        if(existUser == null) {  //User không tồn tại
            return null;
        }

        return addressDao.getAddressByAddressIdAndUserId(addressId, userId);
    }

    @Override
    public boolean addNewAddress(Address newAddress) {
        UserDto existUser = userDao.getUserById(newAddress.getIdUser());
        if(existUser == null) {  //User không tồn tại để thêm mới địa chỉ
            return false;
        }

        return addressDao.addNewAddress(newAddress);
    }

    @Override
    public boolean updateAddress(Address addressUpdate) {
        Address existAddess = addressDao.getAddressById(addressUpdate.getId());
        if(existAddess == null) {  //Address không ton tai để cập nhật
            return false;
        }

        return addressDao.updateAddress(addressUpdate);
    }

    @Override
    public boolean deleteAddress(Integer idAddress, String deletedBy) {
        Address existAddress = addressDao.getAddressById(idAddress);
        if (existAddress == null) { //Địa chỉ không tồn tại để xoá
            return false;
        }

        return addressDao.deleteAddress(idAddress, deletedBy);
    }

    @Override
    public List<Address> getListAddressByUserId(Integer userId) {
        UserDto existUser = userDao.getUserById(userId);
        if(existUser == null) {  //User không tồn tại
            return null;
        }

        return addressDao.getListAddressByUserId(userId);
    }

    public Map<String, Object> getDataFromAPIProvince() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://esgoo.net/api-tinhthanh/1/0.htm");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDataFromAPIDistrict(String idProvince) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://esgoo.net/api-tinhthanh/2/" + idProvince + ".htm");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDataFromAPIWard(String idDistrict) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://esgoo.net/api-tinhthanh/3/" + idDistrict + ".htm");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
