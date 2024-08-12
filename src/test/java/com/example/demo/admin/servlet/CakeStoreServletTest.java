package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CakeService;
import com.example.demo.admin.service.CakeStoreService;
import com.example.demo.admin.servlet.CakeStoreServlet;
import com.example.demo.entity.CakeStore;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CakeStoreServletTest {
    @InjectMocks
    private CakeStoreServlet cakeStoreServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CakeStoreService cakeStoreService;
    @Mock
    private CakeService cakeService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Mockito.when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");
    }

    @Test
    public void viewCakeForSaleOfStore() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/3/list-cake");

        cakeStoreServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/view-cakestore.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewAddNewCakeToStore() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/3/add-new-cake");

        cakeStoreServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/add-cakestore.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void viewUpdateCakeStore() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/3/update-store-cake/3");

        cakeStoreServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/update-cakestore.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void viewBlankPage() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/abc");

        cakeStoreServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher("/WEB-INF/views/blank.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void addNewCakeToStoreSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "1";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("30");
        Mockito.when(request.getParameter("cakeId")).thenReturn("3");
        Mockito.when(request.getParameter("price")).thenReturn("150000");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-15");

        Mockito.when(cakeStoreService.addNewCakeToStore(any(CakeStore.class))).thenReturn(true);

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new cake to store successfully!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/list-cake");
    }

    @Test
    public void addNewCakeToStoreFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("30");
        Mockito.when(request.getParameter("cakeId")).thenReturn("3");
        Mockito.when(request.getParameter("price")).thenReturn("150000");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-15");

        Mockito.when(cakeStoreService.addNewCakeToStore(any(CakeStore.class))).thenReturn(false);

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Add new cake to store failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/add-new-cake");
    }

    @Test
    public void addNewCakeToStoreWithoutCakeId() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("30");
        Mockito.when(request.getParameter("cakeId")).thenReturn("");
        Mockito.when(request.getParameter("price")).thenReturn("150000");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-15");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Please select cake!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/add-new-cake");
    }

    @Test
    public void addNewCakeToStoreEXPBeforeMFG() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("30");
        Mockito.when(request.getParameter("cakeId")).thenReturn("4");
        Mockito.when(request.getParameter("price")).thenReturn("150000");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-05-15");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Expiration date must be after the date of manufacture!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/add-new-cake");
    }

    @Test
    public void addNewCakeToStorePriceLessThanZero() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("30");
        Mockito.when(request.getParameter("cakeId")).thenReturn("4");
        Mockito.when(request.getParameter("price")).thenReturn("-6");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-15");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Price cannot be less than zero!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/add-new-cake");
    }

    @Test
    public void addNewCakeToStorePriceQuantityLessThanZero() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-cake-to-store");
        String storeId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("quantity")).thenReturn("-10");
        Mockito.when(request.getParameter("cakeId")).thenReturn("4");
        Mockito.when(request.getParameter("price")).thenReturn("450000");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-15");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Quantity cannot be less than zero!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/add-new-cake");
    }

    @Test
    public void updateCakeStoreSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store-cake");
        String storeId = "1";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("cakeStoreId")).thenReturn("1");
        Mockito.when(request.getParameter("cakeId")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-30");
        Mockito.when(request.getParameter("price")).thenReturn("120000");
        Mockito.when(request.getParameter("quantity")).thenReturn("50");
        Mockito.when(request.getParameter("status")).thenReturn("1");

        Mockito.when(cakeStoreService.updateCakeStore(any(CakeStore.class))).thenReturn(true);

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update cake for sales successfully!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/list-cake");
    }

    @Test
    public void updateCakeStoreFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store-cake");
        String storeId = "99";
        String cakeStoreId = "99";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("cakeId")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-30");
        Mockito.when(request.getParameter("price")).thenReturn("120000");
        Mockito.when(request.getParameter("quantity")).thenReturn("50");
        Mockito.when(request.getParameter("status")).thenReturn("1");

        Mockito.when(cakeStoreService.updateCakeStore(any(CakeStore.class))).thenReturn(false);

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update cake for sales failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
    }

    @Test
    public void updateCakeStoreEXPBeforeMFG() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store-cake");

        String storeId = "1";
        String cakeStoreId = "2";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("cakeId")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-05-30");
        Mockito.when(request.getParameter("price")).thenReturn("120000");
        Mockito.when(request.getParameter("quantity")).thenReturn("50");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Expiration date must be after the date of manufacture!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
    }

    @Test
    public void updateCakeStorePriceLessThanZero() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store-cake");

        String storeId = "1";
        String cakeStoreId = "2";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("cakeId")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-30");
        Mockito.when(request.getParameter("price")).thenReturn("-120000");
        Mockito.when(request.getParameter("quantity")).thenReturn("50");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Price cannot be less than zero!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
    }

    @Test
    public void updateCakeStoreQuantityLessThanZero() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-store-cake");

        String storeId = "1";
        String cakeStoreId = "2";
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("cakeId")).thenReturn("1");
        Mockito.when(request.getParameter("manufactureDate")).thenReturn("2024-06-30");
        Mockito.when(request.getParameter("expirationDate")).thenReturn("2024-07-30");
        Mockito.when(request.getParameter("price")).thenReturn("120000");
        Mockito.when(request.getParameter("quantity")).thenReturn("-50");

        cakeStoreServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Error: Quantity cannot be less than zero!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
    }

    @Test
    public void deleteCakeForSaleSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-cake-from-store");

        String storeId = "1";
        String cakeStoreId = "1";

        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        String deletedBy = (String) request.getSession().getAttribute("username");

        Mockito.when(cakeStoreService.deleteCakeStore(Integer.valueOf(cakeStoreId), deletedBy)).thenReturn(true);

        cakeStoreServlet.doPost(request, response);
        Mockito.verify(session).setAttribute("messageResponse", "Stop selling successfully!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/list-cake");
    }

    @Test
    public void deleteCakeForSaleFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-cake-from-store");

        String storeId = "99";
        String cakeStoreId = "99";

        Mockito.when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        String deletedBy = (String) request.getSession().getAttribute("username");

        Mockito.when(cakeStoreService.deleteCakeStore(Integer.valueOf(cakeStoreId), deletedBy)).thenReturn(false);

        cakeStoreServlet.doPost(request, response);
        Mockito.verify(session).setAttribute("messageResponse", "Stop selling failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/store/" + storeId + "/list-cake");
    }

}
