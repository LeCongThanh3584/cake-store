package com.example.demo.admin.service;

import com.example.demo.admin.dao.CakeDao;
import com.example.demo.admin.dao.CakeStoreDao;
import com.example.demo.admin.dao.StoreDao;
import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.admin.service.impl.CakeStoreServiceImpl;
import com.example.demo.entity.Cake;
import com.example.demo.entity.CakeStore;
import com.example.demo.entity.Store;
import jakarta.servlet.annotation.MultipartConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

public class CakeStoreServiceTest {
    @Mock
    private CakeStoreDao cakeStoreDao;
    @Mock
    private StoreDao storeDao;
    @Mock
    private CakeDao cakeDao;

    @InjectMocks
    private CakeStoreServiceImpl cakeStoreService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCakeByStore() {
        Integer storeId = 1;

        CakeStoreResponse cakeStoreResponse1 = Mockito.mock(CakeStoreResponse.class);
        CakeStoreResponse cakeStoreResponse2 = Mockito.mock(CakeStoreResponse.class);

        List<CakeStoreResponse> expected = Arrays.asList(cakeStoreResponse1, cakeStoreResponse2);

        Mockito.when(cakeStoreDao.getAllCakeByStore(storeId)).thenReturn(expected);

        List<CakeStoreResponse> actual = cakeStoreService.getAllCakesByStore(storeId);

        assertEquals(expected, actual);
        Mockito.verify(cakeStoreDao, Mockito.times(1)).getAllCakeByStore(storeId);
    }

    @Test
    public void testGetCakeStoreById() {
        Integer cakeStoreId = 1;
        CakeStoreResponse expected = Mockito.mock(CakeStoreResponse.class);

        Mockito.when(cakeStoreDao.getCakeStoreById(cakeStoreId)).thenReturn(expected);

        CakeStoreResponse actual = cakeStoreService.getCakeStoreById(cakeStoreId);

        assertEquals(expected, actual);
        Mockito.verify(cakeStoreDao, Mockito.times(1)).getCakeStoreById(cakeStoreId);
    }

    @Test
    public void testGetCakeForSaleOfStore_StoreNotExist() {
        Integer storeId = 1; String keyword = "abc"; Integer pageNumber = 1; Integer pageSize = 1;

        Mockito.when(storeDao.getStoreById(storeId)).thenReturn(null);
        List<CakeStoreResponse> result = cakeStoreService.getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);

        assertNull(result);
        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(storeId);
        Mockito.verify(cakeStoreDao, Mockito.times(0)).getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);
    }

    @Test
    public void testGetCakeForSaleOfStore_StoreExist() {
        Integer storeId = 1; String keyword = "abc"; Integer pageNumber = 1; Integer pageSize = 1;
        Store existStore = Mockito.mock(Store.class);

        CakeStoreResponse cSR1 = Mockito.mock(CakeStoreResponse.class);
        CakeStoreResponse cSR2 = Mockito.mock(CakeStoreResponse.class);
        List<CakeStoreResponse> expected = Arrays.asList(cSR1, cSR2);

        Mockito.when(storeDao.getStoreById(storeId)).thenReturn(existStore);
        Mockito.when(cakeStoreDao.getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize)).thenReturn(expected);

        List<CakeStoreResponse> actual = cakeStoreService.getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);

        assertEquals(expected, actual);
        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(storeId);
        Mockito.verify(cakeStoreDao, Mockito.times(1)).getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);
    }

    @Test
    public void testAddNewCakeStoreSuccess() {
        CakeStore newCakeStore = new CakeStore(); newCakeStore.setIdStore(1);
        Store existStore = Mockito.mock(Store.class);

        Mockito.when(storeDao.getStoreById(newCakeStore.getIdStore())).thenReturn(existStore);
        Mockito.when(cakeStoreDao.addNewCakeToStore(newCakeStore)).thenReturn(true);

        boolean result = cakeStoreService.addNewCakeToStore(newCakeStore);
        assertTrue(result);

        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(newCakeStore.getIdStore());
        Mockito.verify(cakeStoreDao, Mockito.times(1)).addNewCakeToStore(newCakeStore);
    }

    @Test
    public void testAddNewCakeStoreFailed() {
        CakeStore newCakeStore = new CakeStore(); newCakeStore.setIdStore(1);

        Mockito.when(storeDao.getStoreById(newCakeStore.getIdStore())).thenReturn(null);

        boolean result = cakeStoreService.addNewCakeToStore(newCakeStore);
        assertFalse(result);

        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(newCakeStore.getIdStore());
        Mockito.verify(cakeStoreDao, Mockito.times(0)).addNewCakeToStore(newCakeStore);
    }

    @Test
    public void testUpdateCakeStoreSuccess() {
        CakeStore cakeStoreUpdate = new CakeStore(); cakeStoreUpdate.setIdCake(1); cakeStoreUpdate.setIdStore(1);
        Cake existCake = Mockito.mock(Cake.class);
        Store existStore = Mockito.mock(Store.class);

        Mockito.when(cakeDao.getOneById(cakeStoreUpdate.getIdCake())).thenReturn(existCake);
        Mockito.when(storeDao.getStoreById(cakeStoreUpdate.getIdStore())).thenReturn(existStore);
        Mockito.when(cakeStoreDao.updateCakeStore(cakeStoreUpdate)).thenReturn(true);

        boolean result = cakeStoreService.updateCakeStore(cakeStoreUpdate);
        assertTrue(result);

        Mockito.verify(cakeDao, Mockito.times(1)).getOneById(cakeStoreUpdate.getIdCake());
        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(cakeStoreUpdate.getIdStore());
        Mockito.verify(cakeStoreDao, Mockito.times(1)).updateCakeStore(cakeStoreUpdate);


    }

    @Test
    public void testUpdateCakeStore_CakeNotExist() {
        CakeStore cakeStoreUpdate = new CakeStore(); cakeStoreUpdate.setIdCake(1); cakeStoreUpdate.setIdStore(1);

        Mockito.when(cakeDao.getOneById(cakeStoreUpdate.getIdCake())).thenReturn(null);

        boolean result = cakeStoreService.updateCakeStore(cakeStoreUpdate);
        assertFalse(result);

        Mockito.verify(cakeDao, Mockito.times(1)).getOneById(cakeStoreUpdate.getIdCake());
        Mockito.verify(storeDao, Mockito.times(0)).getStoreById(cakeStoreUpdate.getIdStore());
        Mockito.verify(cakeStoreDao, Mockito.times(0)).updateCakeStore(cakeStoreUpdate);

    }

    @Test
    public void testUpdateCakeStore_StoreNotExist() {
        CakeStore cakeStoreUpdate = new CakeStore(); cakeStoreUpdate.setIdCake(1); cakeStoreUpdate.setIdStore(1);
        Cake existCake = Mockito.mock(Cake.class);

        Mockito.when(cakeDao.getOneById(cakeStoreUpdate.getIdCake())).thenReturn(existCake);
        Mockito.when(storeDao.getStoreById(cakeStoreUpdate.getIdStore())).thenReturn(null);

        boolean result = cakeStoreService.updateCakeStore(cakeStoreUpdate);
        assertFalse(result);

        Mockito.verify(cakeDao, Mockito.times(1)).getOneById(cakeStoreUpdate.getIdCake());
        Mockito.verify(storeDao, Mockito.times(1)).getStoreById(cakeStoreUpdate.getIdStore());
        Mockito.verify(cakeStoreDao, Mockito.times(0)).updateCakeStore(cakeStoreUpdate);

    }

    @Test
    public void testGetTotalPage() {
        String keyword = "abc"; Integer pageSize = 5; Integer storeId = 1;
        Integer ExpectedCount = 20;

        Mockito.when(cakeStoreDao.countCakeStore(keyword, storeId)).thenReturn(20);
        Integer actualCount = cakeStoreService.getTotalPages(keyword, pageSize, storeId);

        assertEquals(Integer.valueOf(4), actualCount);
        Mockito.verify(cakeStoreDao, Mockito.times(1)).countCakeStore(keyword, storeId);
    }

    @Test
    public void testDeleteCakeStoreSuccess() {
        Integer cakeStoreId = 1; String deletedBy = "abc";
        CakeStoreResponse cakeStoreResponse = Mockito.mock(CakeStoreResponse.class);

        Mockito.when(cakeStoreDao.getCakeStoreById(cakeStoreId)).thenReturn(cakeStoreResponse);
        Mockito.when(cakeStoreDao.deleteCakeStore(cakeStoreId, deletedBy)).thenReturn(true);

        boolean result = cakeStoreService.deleteCakeStore(cakeStoreId, deletedBy);
        assertTrue(result);

        Mockito.verify(cakeStoreDao, Mockito.times(1)).getCakeStoreById(cakeStoreId);
        Mockito.verify(cakeStoreDao, Mockito.times(1)).deleteCakeStore(cakeStoreId, deletedBy);
    }

    @Test
    public void testDeleteCakeStoreFailed() {
        Integer cakeStoreId = 1; String deletedBy = "abc";

        Mockito.when(cakeStoreDao.getCakeStoreById(cakeStoreId)).thenReturn(null);

        boolean result = cakeStoreService.deleteCakeStore(cakeStoreId, deletedBy);

        assertFalse(result);

        Mockito.verify(cakeStoreDao, Mockito.times(1)).getCakeStoreById(cakeStoreId);
        Mockito.verify(cakeStoreDao, Mockito.times(0)).deleteCakeStore(cakeStoreId, deletedBy);
    }

}
