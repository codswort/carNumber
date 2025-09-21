package com.example.carnumber;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CarNumberControllerTest {
    @BeforeAll
    static void setUp() {
    }
    @Test
    void testIncrementDigits1() {
        String start = "А000АА";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("А001АА", current);
    }
    @Test
    void testIncrementDigits2() {
        String start = "А009АА";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("А010АА", current);
    }
    @Test
    void testIncrementDigits3() {
        String start = "А099АА";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("А100АА", current);
    }
    @Test
    void testIncrementDigits4() {
        String start = "А999АА";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("А000АВ", current);
    }
    @Test
    void testIncrementDigits5() {
        String start = "А999АХ";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("А000ВА", current);
    }
    @Test
    void testIncrementDigits6() {
        String start = "А999ХХ";
        String current = CarNumberController.numberIncrement(start);
        assertEquals("В000АА", current);
    }
    @Test
    void testIncrementDigits7() {
        String start = "Х999ХХ";
        String current = CarNumberController.numberIncrement(start);
        assertEquals(null, current);
    }
}
