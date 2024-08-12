package com.example.demo.admin.servlet;

import com.example.demo.admin.service.AddressService;
import com.example.demo.admin.servlet.AddressServlet;
import com.example.demo.entity.Address;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class AddressServletTest {
    @InjectMocks
    private AddressServlet addressServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private AddressService addressService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Mockito.when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");
    }

    @Test
    public void viewGetAddress() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/8/view-address");

        addressServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/address/view-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewAddNewAddress() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/8/add-new-address");

        addressServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/address/add-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewUpdateAddress() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/8/update-address/7");
        Mockito.when(addressService.getAddressByAddressIdAndUserId(7,8)).thenReturn(new Address(1,
                "Home", "HaNoi", "CauGiay", "YenHoa", "012345789"
        ));

        Map<String, Object> province = new HashMap<>(); province.put("id", "1"); province.put("full_name", "HaNoi");

        Mockito.when(addressService.getDataFromAPIProvince()).thenReturn(new HashMap<>() {{
            put("data", new ArrayList<>(Arrays.asList(province)));
        }});

        Map<String, Object> district = new HashMap<>(); district.put("id", "1"); district.put("full_name", "CauGiay");

        Mockito.when(addressService.getDataFromAPIDistrict("1")).thenReturn(new HashMap<>() {{
            put("data", new ArrayList<>(Arrays.asList(district)));
        }});

        Map<String, Object> ward = new HashMap<>(); ward.put("id", "1"); ward.put("full_name", "YenHoa");

        Mockito.when(addressService.getDataFromAPIWard("1")).thenReturn(new HashMap<>() {{
            put("data", new ArrayList<>(Arrays.asList(ward)));
        }});

        addressServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/address/update-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void blankPage() throws ServletException, IOException {
        Mockito.when(request.getPathInfo()).thenReturn("/abc");

        addressServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher("/WEB-INF/views/blank.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void addNewAddressSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-address");
        String userId = "1";
        Mockito.when(request.getParameter("userId")).thenReturn(userId);
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("CauGiay");
        Mockito.when(request.getParameter("ward")).thenReturn("YenHoa");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        Mockito.when(addressService.addNewAddress(any(Address.class))).thenReturn(true);

        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new address successfully!");
        Mockito.verify(response).sendRedirect("/admin/user/" + userId + "/view-address");

    }

    @Test
    public void addNewAddressFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-address");

        String userId = "99";
        Mockito.when(request.getParameter("userId")).thenReturn(userId);
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("CauGiay");
        Mockito.when(request.getParameter("ward")).thenReturn("YenHoa");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        Mockito.when(addressService.addNewAddress(any(Address.class))).thenReturn(false);

        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new address failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/user/" + userId + "/add-new-address");
    }

    @Test
    public void updateAddressSuccess() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-address");
        String addressId = "1"; String userId = "1";

        Mockito.when(request.getParameter("addressId")).thenReturn(addressId);
        Mockito.when(request.getParameter("userId")).thenReturn(userId);
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("CauGiay");
        Mockito.when(request.getParameter("ward")).thenReturn("YenHoa");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        Mockito.when(addressService.updateAddress(any(Address.class))).thenReturn(true);
        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update address with id " + addressId + " successfully!");
        Mockito.verify(response).sendRedirect("/admin/user/" + userId + "/view-address");
    }

    @Test
    public void updateAddressFailed() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-address");
        String addressId = "99"; String userId = "99";

        Mockito.when(request.getParameter("addressId")).thenReturn(addressId);
        Mockito.when(request.getParameter("userId")).thenReturn(userId);
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("CauGiay");
        Mockito.when(request.getParameter("ward")).thenReturn("YenHoa");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        Mockito.when(addressService.updateAddress(any(Address.class))).thenReturn(false);
        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update address with id " + addressId +" failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/user/" + userId + "/update-address/" + addressId);
    }

    @Test
    public void deleteAddressSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-address");
        String idAddress = "1";
        String idUser = "1";
        Mockito.when(request.getParameter("idAddressDelete")).thenReturn(idAddress);
        Mockito.when(request.getParameter("idUser")).thenReturn(idUser);
        String deletedBy = (String) request.getSession().getAttribute("username");

        Mockito.when(addressService.deleteAddress(Integer.valueOf(idAddress), deletedBy)).thenReturn(true);
        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete address with id " + idAddress + " successfully!");
        Mockito.verify(response).sendRedirect("/admin/user/" + idUser + "/view-address");
    }

    @Test
    public void deleteAddressFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-address");
        String idAddress = "1";
        String idUser = "1";
        Mockito.when(request.getParameter("idAddressDelete")).thenReturn(idAddress);
        Mockito.when(request.getParameter("idUser")).thenReturn(idUser);
        String deletedBy = (String) request.getSession().getAttribute("username");

        Mockito.when(addressService.deleteAddress(Integer.valueOf(idAddress), deletedBy)).thenReturn(false);
        addressServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete address with id " + idAddress + " failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/user/" + idUser + "/view-address");

    }

}
