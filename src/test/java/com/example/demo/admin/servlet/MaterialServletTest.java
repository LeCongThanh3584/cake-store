package com.example.demo.admin.servlet;

import com.example.demo.admin.service.MaterialService;
import com.example.demo.entity.Material;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class MaterialServletTest {

    @InjectMocks
    private MaterialServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private StringWriter responseWriter;

    @Mock
    private MaterialService materialService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new MaterialServlet(materialService);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }


    @ParameterizedTest
    @CsvSource({
            ",1", "hello,1", ","
    })
    public void testDoGetView(String search, String page) throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/view");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("page")).thenReturn(page);
        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/material/view-material.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetCreate() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/create");
        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/material/add-material.jsp");
    }

    @ParameterizedTest
    @CsvSource({
            "hello, , Phạm thanh sơn, sonpt",
            "hello1, 1928322, , sonpt"
    })
    public void testDoPostCreateSuccess(String name, String code, String description, String usernmae) throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/create");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("description")).thenReturn(description);
        Mockito.when(materialService.createMaterial(Mockito.any(Material.class))).thenReturn(true);
        Mockito.when(session.getAttribute("username")).thenReturn(usernmae);

        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/material/view");
        Mockito.verify(session).setAttribute("response", "add material succesfully!");
    }

    @ParameterizedTest
    @CsvSource({
            ", 1928392, Phạm thanh sơn"
    })
    public void testDoPostCreateFail(String name, String code, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/create");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("description")).thenReturn(description);

        servlet.doPost(request, response);
        Mockito.verify(request).setAttribute("errors", "name is required");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/material/add-material.jsp");
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void testDoGetUpdate(String id) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/update");
        Mockito.when(request.getParameter("id")).thenReturn(id);

        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/material/update-material.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "1,helloCode,helloName, this is descripiton",
            "1,helloCode,helloNameTest, this is descripiton",
            "1,helloCodeTest,helloName, this is descripiton",
            "1,helloCode,helloName, this is descripitonTest",
    })
    public void testDoPostUpdateSuccess(String id, String code, String name, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/update");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(request.getParameter("description")).thenReturn(description);
        Mockito.when(session.getAttribute("username")).thenReturn("psonne");

        Material material = new Material();
        material.setId(Integer.valueOf(id));
        material.setCode(ArgumentMatchers.any());
        material.setName(name);
        material.setDescription(description);

        Mockito.when(materialService.updateMaterial(material)).thenReturn(true);
        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/material/view");
        Mockito.verify(session).setAttribute("response", "update material succesfully!");
    }

    @ParameterizedTest
    @CsvSource({
            "1,,, this is descripiton",
            "1,helloCode,, this is descripiton",
            "1,,helloName, this is descripiton",
            "1,helloCode,helloName,",
    })
    public void testDoPostUpdateFail(String id, String code, String name, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/update");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(request.getParameter("description")).thenReturn(description);
        Mockito.when(session.getAttribute("username")).thenReturn("psonne");


        Material material = new Material();
        material.setId(Integer.valueOf(id));
        material.setCode(ArgumentMatchers.any());
        material.setName(name);
        material.setDescription(description);

        Mockito.when(materialService.updateMaterial(material)).thenReturn(true);
        servlet.doPost(request, response);

        Mockito.verify(request).setAttribute("errors", Map.of("name", "name is required", "code", "code is required"));
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/material/update-material.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "7", "21", "22"
    })
    public void testDoGetDeleteSuccess(String id) throws ServletException, IOException {
        Material material = new Material();
        material.setId(Integer.valueOf(id));
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/delete");
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("sonpt");
        Mockito.when(materialService.getOneById(Long.valueOf(id))).thenReturn(material);
        Mockito.when(materialService.deleteById(material)).thenReturn(true);
        servlet.doGet(request, response);
        Mockito.verify(response).sendRedirect("/admin/material/view");
        Mockito.verify(session).setAttribute("response", "Delete material successfully!");
    }

    @ParameterizedTest
    @CsvSource({
            "1", "18", "19", "20"
    })
    public void testDoGetDeleteFail(String id) throws ServletException, IOException {
        Material material = new Material();
        material.setId(Integer.valueOf(id));
        Mockito.when(request.getRequestURI()).thenReturn("/admin/material/delete");
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("sonpt");
        Mockito.when(materialService.getOneById(Long.valueOf(id))).thenReturn(material);
        Mockito.when(materialService.deleteById(material)).thenReturn(false);
        servlet.doGet(request, response);
        Mockito.verify(response).sendRedirect("/admin/material/view");
        Mockito.verify(session).setAttribute("response", "Delete material failed!");
    }


}
