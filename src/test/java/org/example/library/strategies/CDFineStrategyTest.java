package org.example.library.strategies;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CDFineStrategyTest {

    @Test
    void testFineForZeroDays() {
        CDFineStrategy strategy = new CDFineStrategy();
        assertEquals(0.0, strategy.calculateFine(0));
    }

    @Test
    void testFineForPositiveDays() {
        CDFineStrategy strategy = new CDFineStrategy();
        assertEquals(60.0, strategy.calculateFine(3));
        assertEquals(200.0, strategy.calculateFine(10));
    }

    @Test
    void testFineForLargeNumberOfDays() {
        CDFineStrategy strategy = new CDFineStrategy();
        assertEquals(2000.0, strategy.calculateFine(100));
    }

    @Test
    void testFineForNegativeDays() {
        CDFineStrategy strategy = new CDFineStrategy();
        assertEquals(-40.0, strategy.calculateFine(-2));
    }
}
