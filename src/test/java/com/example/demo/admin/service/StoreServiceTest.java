package com.example.demo.admin.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.admin.dao.StoreDao;
import com.example.demo.admin.service.impl.StoreServiceImpl;
import com.example.demo.entity.Store;
import com.example.demo.util.ThirdApiConfig;
import com.example.demo.util.UploadUtil;
import jakarta.servlet.http.Part;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;

public class StoreServiceTest {
    @InjectMocks
    private StoreServiceImpl storeService;
    @Mock
    private StoreDao storeDao;
    @Mock
    private Part imageStore;
    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStore() {

        Store store1 = mock(Store.class);
        Store store2 = mock(Store.class);

        System.out.println(Math.PI);

        List<Store> expectListStore = Arrays.asList(store1, store2);
        Mockito.when(storeDao.getAllStores()).thenReturn(expectListStore);

        List<Store> actualListStore = storeService.getAllStores();
        assertEquals(expectListStore, actualListStore);
        verify(storeDao, times(1)).getAllStores();
    }

    @Test
    public void testGetAllStoresPaginationSearch() {
        Integer pageNumber = 1;
        Integer pageSize = 5;
        String keyWord = "abc";

        Store store1 = mock(Store.class);
        Store store2 = mock(Store.class);

        List<Store> expectListStore = Arrays.asList(store1, store2);

        Mockito.when(storeDao.getAllStoresPaginationSearch(pageNumber, pageSize, keyWord)).thenReturn(expectListStore);

        List<Store> actualListStore = storeService.getAllStoresPaginationSearch(pageNumber, pageSize, keyWord);

        assertEquals(expectListStore, actualListStore);
        verify(storeDao, times(1)).getAllStoresPaginationSearch(pageNumber, pageSize, keyWord);

    }

    @Test
    public void testDeleteStoreSuccess() {
        Integer storeId = 1; String deleted_by = "abc";
        Store existStore = Mockito.mock(Store.class);

        Mockito.when(storeDao.getStoreById(storeId)).thenReturn(existStore);
        Mockito.when(storeDao.deleteStore(storeId, deleted_by)).thenReturn(true);

        boolean result = storeService.deleteStore(storeId, deleted_by);

        assertTrue(result);

        Mockito.verify(storeDao, times(1)).getStoreById(storeId);
        Mockito.verify(storeDao, times(1)).deleteStore(storeId, deleted_by);

    }

    @Test
    public void testDeleteStoreFailed() {
        Integer storeId = 1; String deleted_by = "abc";

        Mockito.when(storeDao.getStoreById(storeId)).thenReturn(null);

        boolean result = storeService.deleteStore(storeId, deleted_by);

        assertFalse(result);

        Mockito.verify(storeDao, times(1)).getStoreById(storeId);
        Mockito.verify(storeDao, times(0)).deleteStore(storeId, deleted_by);
    }

    @Test
    public void testGetStoreById() {
        Integer storeId = 1;
        Store expectedStore = Mockito.mock(Store.class);

        Mockito.when(storeDao.getStoreById(storeId)).thenReturn(expectedStore);

        Store actualStore = storeService.getStoreById(storeId);

        assertEquals(expectedStore,actualStore);
        Mockito.verify(storeDao, times(1)).getStoreById(storeId);

    }

    @Test
    public void testGetTotalPages() {
        String keyword = "abc"; int pageSize = 5;
        int count = 20;
        Mockito.when(storeDao.countStore(keyword)).thenReturn(count);

        Integer totalPage = storeService.getTotalPages(keyword, pageSize);
        assertEquals(Integer.valueOf(4), totalPage);

        Mockito.verify(storeDao, times(1)).countStore(keyword);

    }

}
