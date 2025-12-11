package org.example.library.strategies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookFineStrategyTest {

    @Test
    void testFineZeroDays() {
        BookFineStrategy strategy = new BookFineStrategy();
        assertEquals(0, strategy.calculateFine(0));
    }

    @Test
    void testFinePositiveDays() {
        BookFineStrategy strategy = new BookFineStrategy();
        assertEquals(50, strategy.calculateFine(5));   // 5 * 10
        assertEquals(100, strategy.calculateFine(10)); // 10 * 10
    }

    @Test
    void testFineLargeNumber() {
        BookFineStrategy strategy = new BookFineStrategy();
        assertEquals(1000, strategy.calculateFine(100)); // 100 * 10
    }

    @Test
    void testNegativeDays() {
        BookFineStrategy strategy = new BookFineStrategy();
        assertEquals(-20, strategy.calculateFine(-2)); // -2 * 10
    }
}
