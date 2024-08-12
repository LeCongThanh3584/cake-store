package com.example.demo.admin.service;

import com.example.demo.admin.dao.AddressDao;
import com.example.demo.admin.dao.UserDao;
import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.impl.AddressServiceImpl;
import com.example.demo.entity.Address;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class AddressServiceTest {
    @InjectMocks
    private AddressServiceImpl addressService;
    @Mock
    private AddressDao addressDao;
    @Mock
    private UserDao userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAddressById() {
        Integer idAddress = 1;
        Address expectedAddress = Mockito.mock(Address.class);
        Mockito.when(addressDao.getAddressById(idAddress)).thenReturn(expectedAddress);

        Address actualAddress = addressService.getAddressById(idAddress);
        assertEquals(expectedAddress, actualAddress);

        Mockito.verify(addressDao, Mockito.times(1)).getAddressById(idAddress);

    }

    @Test
    public void testGetAddressByAddressIdAndUserId_UserExist() {
        Integer addressId = 1; Integer userId = 1;
        UserDto existUser = Mockito.mock(UserDto.class);
        Address expectedAddress = Mockito.mock(Address.class);

        Mockito.when(userDao.getUserById(userId)).thenReturn(existUser);
        Mockito.when(addressDao.getAddressByAddressIdAndUserId(addressId, userId)).thenReturn(expectedAddress);

        Address actualAddress = addressService.getAddressByAddressIdAndUserId(addressId, userId);

        assertNotNull(actualAddress);
        assertEquals(expectedAddress, actualAddress);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(userId);
        Mockito.verify(addressDao, Mockito.times(1)).getAddressByAddressIdAndUserId(addressId, userId);


    }

    @Test
    public void testGetAddressByAddressIdAndUserId_UserNotExist() {
        Integer addressId = 1; Integer userId = 1;

        Mockito.when(userDao.getUserById(userId)).thenReturn(null);

        Address actualAddress = addressService.getAddressByAddressIdAndUserId(addressId, userId);

        assertNull(actualAddress);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(userId);
        Mockito.verify(addressDao, Mockito.never()).getAddressByAddressIdAndUserId(addressId, userId);


    }

    @Test
    public void testAddNewAddress_UserNotExist() {
        Address newAddress = new Address(); newAddress.setIdUser(1);

        Mockito.when(userDao.getUserById(newAddress.getIdUser())).thenReturn(null);
        boolean result = addressService.addNewAddress(newAddress);

        assertFalse(result);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(newAddress.getIdUser());
        Mockito.verify(addressDao, Mockito.never()).addNewAddress(newAddress);

    }

    @Test
    public void testAddNewAddress_UserExist() {
        Address newAddress = new Address(); newAddress.setIdUser(1);
        UserDto existUser = Mockito.mock(UserDto.class);

        Mockito.when(userDao.getUserById(newAddress.getIdUser())).thenReturn(existUser);
        Mockito.when(addressDao.addNewAddress(newAddress)).thenReturn(true);

        boolean result = addressService.addNewAddress(newAddress);

        assertTrue(result);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(newAddress.getIdUser());
        Mockito.verify(addressDao, Mockito.times(1)).addNewAddress(newAddress);

    }

    @Test
    public void testUpdateAddressSuccess() {
        Address addressUpdate = new Address(); addressUpdate.setId(1);
        Address existAddress = Mockito.mock(Address.class);

        Mockito.when(addressDao.getAddressById(addressUpdate.getId())).thenReturn(existAddress);
        Mockito.when(addressDao.updateAddress(addressUpdate)).thenReturn(true);

        boolean result = addressService.updateAddress(addressUpdate);

        assertTrue(result);

        Mockito.verify(addressDao, Mockito.times(1)).getAddressById(addressUpdate.getId());
        Mockito.verify(addressDao, Mockito.times(1)).updateAddress(addressUpdate);
    }

    @Test
    public void testUpdateAddressFailed() {
        Address addressUpdate = new Address(); addressUpdate.setId(1);

        Mockito.when(addressDao.getAddressById(addressUpdate.getId())).thenReturn(null);
        boolean result = addressService.updateAddress(addressUpdate);

        assertFalse(result);

        Mockito.verify(addressDao, Mockito.times(1)).getAddressById(addressUpdate.getId());
        Mockito.verify(addressDao, Mockito.never()).updateAddress(addressUpdate);
    }

    @Test
    public void testDeleteAddressSuccess() {
        Integer idAddress = 1;
        String deletedBy = "abc";
        Address existAddress = Mockito.mock(Address.class);

        Mockito.when(addressDao.getAddressById(idAddress)).thenReturn(existAddress);
        Mockito.when(addressDao.deleteAddress(idAddress, deletedBy)).thenReturn(true);

        boolean result = addressService.deleteAddress(idAddress, deletedBy);

        assertTrue(result);
        Mockito.verify(addressDao, Mockito.times(1)).getAddressById(idAddress);
        Mockito.verify(addressDao, Mockito.times(1)).deleteAddress(idAddress, deletedBy);

    }

    @Test
    public void testDeleteAddressFailed() {
        Integer idAddress = 1;
        String deletedBy = "abc";

        Mockito.when(addressDao.getAddressById(idAddress)).thenReturn(null);

        boolean result = addressService.deleteAddress(idAddress, deletedBy);

        assertFalse(result);

        Mockito.verify(addressDao, Mockito.times(1)).getAddressById(idAddress);
        Mockito.verify(addressDao, Mockito.times(0)).deleteAddress(idAddress, deletedBy);

    }

    @Test
    public void testGetListAddressByUserId() {
        Integer userId = 1;
        UserDto existUser = Mockito.mock(UserDto.class);

        Address address1 = Mockito.mock(Address.class);
        Address address2 = Mockito.mock(Address.class);

        List<Address> expected = Arrays.asList(address1, address2);

        Mockito.when(userDao.getUserById(userId)).thenReturn(existUser);
        Mockito.when(addressDao.getListAddressByUserId(userId)).thenReturn(expected);

        List<Address> actual = addressService.getListAddressByUserId(userId);

        assertEquals(expected, actual);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(userId);
        Mockito.verify(addressDao, Mockito.times(1)).getListAddressByUserId(userId);
    }


}
