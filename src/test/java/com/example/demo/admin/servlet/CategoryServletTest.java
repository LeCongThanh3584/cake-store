package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CategoryService;
import com.example.demo.entity.Category;
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

public class CategoryServletTest {

    @InjectMocks
    private CategoryServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private StringWriter responseWriter;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new CategoryServlet(categoryService);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }


    @ParameterizedTest
    @CsvSource({
            ",1", "hello,1", ","
    })
    public void testDoGetView(String search, String page) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/view");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("page")).thenReturn(page);
        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/category/view-category.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoGetCreate() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/create");
        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/category/add-category.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "Banh my, , ", "banh my, 123232,", "banh my, 12323, this is description"
    })
    public void testDoPostCreateSuccess(String name, String code, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/create");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("description")).thenReturn(description);
        Mockito.when(session.getAttribute("username")).thenReturn("pson");

        Category category = new Category();
        category.setName(name);
        category.setCode(ArgumentMatchers.any());
        category.setDescription(description);

        Mockito.when(categoryService.createCategory(category)).thenReturn(true);
        servlet.doPost(request, response);
        Mockito.verify(response).sendRedirect("/admin/category/view");
        Mockito.verify(session).setAttribute("response", "create category succesfully!");
    }

    @ParameterizedTest
    @CsvSource({
            ", , ", ", 123232,", ", 12323, this is description"
    })
    public void tesetDoPostCreateFail(String name, String code, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/create");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("description")).thenReturn(description);
        Mockito.when(session.getAttribute("username")).thenReturn("pson");

        Category category = new Category();
        category.setName(name);
        category.setCode(ArgumentMatchers.any());
        category.setDescription(description);

        Mockito.when(categoryService.createCategory(category)).thenReturn(true);
        servlet.doPost(request, response);
        Mockito.verify(request).setAttribute("errors", "name is required");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/category/add-category.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);

    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4"
    })
    public void testDoGetUpdate(String id) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/update");
        Mockito.when(request.getParameter("id")).thenReturn(id);
        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/category/update-category.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "1,132432,hello,this is description",
            "2,,hello,this is description",
            "3,132432,,this is description",
            "4,132432,hello,",
            "5,,hello,",
    })
    public void testDoPostUpdateSuccess(String id, String code, String name, String description) throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/update");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("psonne");
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("description")).thenReturn(description);

        Category category = new Category();
        category.setId(Integer.valueOf(id));
        category.setName(name);
        category.setCode(ArgumentMatchers.any());
        category.setDescription(description);
        category.setUpdatedBy("psonne");
        Mockito.when(categoryService.updateCategory(category)).thenReturn(true);

        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/category/view");
        Mockito.verify(session).setAttribute("response", "update category succesfully!");
    }

    @ParameterizedTest
    @CsvSource({
            "1,132432,hello,this is description",
            "2,,hello,this is description",
            "3,132432,,this is description",
            "4,132432,hello,",
            "5,,hello,",
    })
    public void testDoPostUpdateFail(String id, String code, String name, String description) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/update");
        Mockito.when(request.getParameter("name")).thenReturn(name);
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("psonne");
        Mockito.when(request.getParameter("code")).thenReturn(code);
        Mockito.when(request.getParameter("code")).thenReturn(description);

        Category category = new Category();
        category.setId(Integer.valueOf(id));
        category.setName(name);
        category.setCode(ArgumentMatchers.any());
        category.setDescription(description);
        Mockito.when(categoryService.updateCategory(category)).thenReturn(true);

        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/category/view");
        Mockito.verify(session).setAttribute("response", "update category succesfully!");
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void testDoGetDeleteSuccess(String id) throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/delete");
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("pson");
        Category category = new Category();
        Mockito.when(categoryService.getOneById(Long.valueOf(id))).thenReturn(category);
        Mockito.when(categoryService.deleteCategory(category)).thenReturn(true);
        servlet.doGet(request, response);
        Mockito.verify(session).setAttribute("response", "Delete category successfully!");
        Mockito.verify(response).sendRedirect("/admin/category/view");
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void testDoDeleteFaild(String id) throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/category/delete");
        Mockito.when(request.getParameter("id")).thenReturn(id);
        Mockito.when(session.getAttribute("username")).thenReturn("pson");
        Category category = new Category();
        Mockito.when(categoryService.getOneById(Long.valueOf(id))).thenReturn(category);
        Mockito.when(categoryService.deleteCategory(category)).thenReturn(true);
        servlet.doGet(request, response);
        Mockito.verify(session).setAttribute("response", "Delete category successfully!");
        Mockito.verify(response).sendRedirect("/admin/category/view");
    }


}
