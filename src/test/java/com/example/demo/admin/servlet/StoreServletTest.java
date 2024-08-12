package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CategoryService;
import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.impl.StoreServiceImpl;
import com.example.demo.admin.servlet.StoresServlet;
import com.example.demo.entity.Store;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StoreServletTest {
    @InjectMocks
    private StoresServlet storesServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private StringWriter stringWriter;
    @Mock
    private HttpSession session;
    @Mock
    private Part imagePart;
    @Mock
    private StoreService storeService;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");

        Mockito.when(imagePart.getSubmittedFileName()).thenReturn("example.jpg");
        Mockito.when(imagePart.getSize()).thenReturn(1024L);
        Mockito.when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("image data".getBytes()));
    }

    @Test
    public void viewGetAllStore() throws ServletException, IOException {
       Mockito.when(request.getRequestURI()).thenReturn("/admin/stores");

       storesServlet.doGet(request, response);

       Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/store/view-stores.jsp");
       Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewAddNewStore() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-store");

        storesServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/store/add-store.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void viewUpdateStore() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store");
        Mockito.when(request.getParameter("id")).thenReturn("1");

        storesServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/store/update-store.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void AddNewStoreSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-store");
        Mockito.when(request.getParameter("nameStore")).thenReturn("nameStore");
        Mockito.when(request.getParameter("address")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        when(request.getPart("image")).thenReturn(mock(Part.class));
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");

        when(storeService.addNewStore(any(Store.class), any(Part.class))).thenReturn(true);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new store successfully!");
        Mockito.verify(response).sendRedirect("/admin/stores");

    }

    @Test
    public void AddNewStoreFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-store");
        Mockito.when(request.getParameter("nameStore")).thenReturn("nameStore");
        Mockito.when(request.getParameter("address")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getPart("image")).thenReturn(mock(Part.class));
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");

        when(storeService.addNewStore(any(Store.class), any(Part.class))).thenReturn(false);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new store failed, please try again!");
        Mockito.verify(response).sendRedirect("/admin/add-store");

    }

    @Test
    public void updateStoreSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store");

        String code = "123456";
        String idStore = "1";
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("id")).thenReturn(idStore);
        Mockito.when(request.getParameter("name")).thenReturn("nameStore");
        Mockito.when(request.getParameter("address")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getPart("image")).thenReturn(mock(Part.class));

        Mockito.when(storeService.updateStore(any(Store.class), any(Part.class))).thenReturn(true);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update store with code " + code + " successfully!");
        Mockito.verify(response).sendRedirect("/admin/stores");
    }

    @Test
    public void updateStoreFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store");

        String code = "123456";
        String idStore = "99";
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("id")).thenReturn(idStore);
        Mockito.when(request.getParameter("name")).thenReturn("nameStore");
        Mockito.when(request.getParameter("address")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getPart("image")).thenReturn(mock(Part.class));

        Mockito.when(storeService.updateStore(any(Store.class), any(Part.class))).thenReturn(false);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update store with code " + code + " failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/update-store?id=" + idStore);
    }

    @Test
    public void DeleteStoreSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-store");
        String idStore = "3";
        Mockito.when(request.getParameter("idStore")).thenReturn(idStore);
        String deleted_by = (String) request.getSession().getAttribute("username");

        Mockito.when(storeService.deleteStore(Integer.valueOf(idStore), deleted_by)).thenReturn(true);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete store with id " + idStore + " successfully!");
        Mockito.verify(response).sendRedirect("/admin/stores");
    }

    @Test
    public void DeleteStoreFail() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-store");
        String idStore = "99";
        Mockito.when(request.getParameter("idStore")).thenReturn(idStore);
        String deleted_by = (String) request.getSession().getAttribute("username");

        Mockito.when(storeService.deleteStore(Integer.valueOf(idStore), deleted_by)).thenReturn(false);

        storesServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete store with id " + idStore + " failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/stores");
    }
}
