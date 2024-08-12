package com.example.demo.admin.service;

import com.example.demo.entity.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {
    Address getAddressById(Integer idAddress);
    Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId);
    boolean addNewAddress(Address newAddress);
    boolean updateAddress(Address addressUpdate);
    boolean deleteAddress(Integer idAddress, String deletedBy);
    List<Address> getListAddressByUserId(Integer userId);
    Map<String, Object> getDataFromAPIProvince();
    Map<String, Object> getDataFromAPIDistrict(String idProvince);
    Map<String, Object> getDataFromAPIWard(String idDistrict);
}
