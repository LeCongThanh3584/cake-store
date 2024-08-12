package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CakeService;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CakeServletTest {

    @InjectMocks
    private CakeServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Part part;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private CakeService cakeService;

    @Mock
    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new CakeServlet();

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }

    @ParameterizedTest
    @CsvSource({
            ",", "hello,1", "hello, "
    })
    public void testDoGetView(String search, String id) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/cake/view");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("page")).thenReturn(id);

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/cake/view-cake.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetCreate() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/cake/create");
        Mockito.when(request.getParameter("materials")).thenReturn(null);
        Mockito.when(request.getParameter("categories")).thenReturn(null);

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/cake/add-cake.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "name, code, 10, color, size, 10, 10, 1, description, 10, input2"

    })
    public void testDoPostCreateSuccess(String name, String code, String weight, String color, String size, String height,
                                        String length, String idCategory, String description, String input1, String input2) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/cake/create");
        Mockito.when(request.getParameter("cakeName")).thenReturn(name);
        Mockito.when(request.getParameter("cakeCode")).thenReturn(code);
        Mockito.when(request.getParameter("cakeWeight")).thenReturn(weight);
        Mockito.when(request.getParameter("cakeColor")).thenReturn(color);
        Mockito.when(request.getParameter("cakeSize")).thenReturn(size);
        Mockito.when(request.getParameter("cakeHeight")).thenReturn(height);
        Mockito.when(request.getParameter("cakeLength")).thenReturn(length);
        Mockito.when(request.getParameter("idCategory")).thenReturn(idCategory);
        Mockito.when(request.getParameter("descripiton")).thenReturn(description);
        Mockito.when(session.getAttribute("username")).thenReturn("sonpttp");


        String imagePath = "D:\\Users\\sonpt51\\Downloads\\logo_full_sm.webp";
        File file = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        Mockito.when(part.getInputStream()).thenReturn(fileInputStream);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("materials[1][input1]", new String[]{input1});
        parameterMap.put("materials[1][input2]", new String[]{input2});
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);


        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/cake/view");
        Mockito.verify(session).setAttribute("response", "Add new cake successfully!");
    }

    @Test
    public void testDoPostCreateFail() {

    }

    @Test
    public void testDoGetUpdate() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/cake/update");
        Mockito.when(request.getParameter("materials")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("categories")).thenReturn(null);
        Mockito.when(request.getParameter("cake")).thenReturn(null);
        Mockito.when(request.getParameter("materialCakes")).thenReturn(null);

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/cake/update-cake.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostUpdateSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/cake/update");
        Mockito.when(request.getParameter("cakeName")).thenReturn("bánh ngọt");
        Mockito.when(request.getParameter("cakeCode")).thenReturn("23423423");
        Mockito.when(request.getParameter("cakeWeight")).thenReturn("12");
        Mockito.when(request.getParameter("cakeColor")).thenReturn("đỏ");
        Mockito.when(request.getParameter("cakeSize")).thenReturn("XL");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("cakeHeight")).thenReturn("12");
        Mockito.when(request.getParameter("cakeLength")).thenReturn("12");
        Mockito.when(request.getParameter("idCategory")).thenReturn("1");
        Mockito.when(request.getParameter("description")).thenReturn("Delicious chocolate cake");
        Mockito.when(session.getAttribute("username")).thenReturn("sonpttp");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getPart("image")).thenReturn(null);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("materials[1][input1]", new String[]{"100"});
        parameterMap.put("materials[1][input2]", new String[]{"Sugar"});
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);

        servlet.doPost(request, response);

        Mockito.verify(session).setAttribute("response", "update cake successfully!");
        Mockito.verify(response).sendRedirect("/admin/cake/view");
    }

    @Test
    public void testDoPostUpdateFail() {
    }


}
