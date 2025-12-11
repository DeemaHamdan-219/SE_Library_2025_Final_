package org.example.library.strategies;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JournalFineStrategyTest {

    @Test
    void testCalculateFine_PositiveDays() {
        JournalFineStrategy strategy = new JournalFineStrategy();
        assertEquals(45, strategy.calculateFine(3)); // 3 × 15
    }

    @Test
    void testCalculateFine_ZeroDays() {
        JournalFineStrategy strategy = new JournalFineStrategy();
        assertEquals(0, strategy.calculateFine(0));
    }

    @Test
    void testCalculateFine_NegativeDays() {
        JournalFineStrategy strategy = new JournalFineStrategy();
        assertEquals(-30, strategy.calculateFine(-2)); // -2 × 15
    }
}
