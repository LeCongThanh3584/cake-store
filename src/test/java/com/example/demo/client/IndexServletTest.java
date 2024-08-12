package com.example.demo.client;

import com.example.demo.client.service.impl.CakeServiceImpl;
import jakarta.servlet.http.HttpServlet;
import org.junit.jupiter.api.BeforeEach;

public class IndexServletTest extends HttpServlet {
    private CakeServiceImpl cakeService;

    @BeforeEach
    public void setup() {
        cakeService = new CakeServiceImpl();
    }


//    @Test
//    public void testOriginalVersion() {
//        List<CakeViewDto> list = cakeService.GetRecommendation();
//        assertNotNull(list);
//    }
}