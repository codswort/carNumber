package com.example.carnumber;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarNumberControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    String regex = "[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2} 116 RUS";
    @LocalServerPort
    private int port;
    private String baseUrl() {
        return "http://localhost:" + port + "/number";
    }
    @Test
    void testRandomGeneratesValidNumbers() {
        String response = restTemplate.getForObject(baseUrl() + "/random", String.class);
        assertNotNull(response);
        System.out.println(response);
        assertTrue(response.matches(regex), "Неверный формат: " + response);
    }
    @Test
    void testNextNumber1() {
        String response = restTemplate.getForObject(baseUrl() + "/next", String.class);
        assertNotNull(response);
        System.out.println(response);
        assertEquals(response, "А000АА 116 RUS");
    }
    @Test
    void testNextNumber2() {
        String response;
        do {
            response = restTemplate.getForObject(baseUrl() + "/next", String.class);
        } while(!response.equals("А009АА 116 RUS"));
        response = restTemplate.getForObject(baseUrl() + "/next", String.class);
        assertNotNull(response);
        System.out.println(response);
        assertEquals(response, "А010АА 116 RUS");
    }
    @Test
    void testNextNumber3() {
        String response;
        do {
            response = restTemplate.getForObject(baseUrl() + "/next", String.class);
        } while(!response.equals("А999АА 116 RUS"));
        response = restTemplate.getForObject(baseUrl() + "/next", String.class);
        assertNotNull(response);
        System.out.println(response);
        assertEquals(response, "А000АВ 116 RUS");
    }
}
