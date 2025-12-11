package org.example.library.strategies;

/**
 * Fine calculation strategy for CDs.
 *
 * This class implements the {@link FineStrategy} interface and defines
 * the fine rule for overdue CDs, where:
 *
 * <ul>
 *     <li>Fine = overdueDays × 20 NIS</li>
 * </ul>
 *
 * CDs have a higher daily fee than books (which use 10 NIS/day),
 * therefore this strategy applies 20 NIS per overdue day.
 *
 * Example:
 * <pre>
 *     FineStrategy strategy = new CDFineStrategy();
 *     double fine = strategy.calculateFine(3); // → 60.0
 * </pre>
 *
 * @author Dima & Asmaa
 * @version 1.0
 */
public class CDFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 20;  // 20 NIS per day
    }
}
