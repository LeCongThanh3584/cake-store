package com.example.demo.client.service;

import com.example.demo.entity.Address;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AddressService {

    List<Address> getAddresses(int userId);

    Address getAddress(int userId, Integer addressId);

    List<Address> getAddressByUserId(int page, int pageSize, int userId, String search);

    Address getAddressById(long id);

    boolean createAddress(Address address);

    boolean updateAddress(Address address);

    int getTotalRecord(int userId, String search);

    Map<String, String> validateCreate(HttpServletRequest request) throws IOException;

    Map<String, String> validateUpdate(HttpServletRequest request) throws IOException;

    Map<String, Object> getProvince();

    Map<String, Object> getDistrict(String idProvince);

    Map<String, Object> getWards(String idDistrict);

}
