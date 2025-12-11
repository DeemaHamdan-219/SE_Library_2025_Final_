package org.example.library.strategies;

/**
 * Fine calculation strategy for journals.
 *
 * This class implements the {@link FineStrategy} interface and provides
 * the rule for calculating overdue fines for journals.
 *
 * <p><b>Rule:</b></p>
 * <ul>
 *     <li>Fine = overdueDays × 15 NIS</li>
 * </ul>
 *
 * Journals typically have a moderate overdue fine compared to:
 * <ul>
 *     <li>Books → 10 NIS per day</li>
 *     <li>CDs → 20 NIS per day</li>
 * </ul>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 *     FineStrategy strategy = new JournalFineStrategy();
 *     double fine = strategy.calculateFine(4);  // → 60.0
 * </pre>
 *
 * <p>This class contains no internal state and is fully stateless,
 * meaning one instance can be reused safely across the system.</p>
 *
 * @author Dima and Asmaa
 * @version 1.0
 */
public class JournalFineStrategy implements FineStrategy {

    /**
     * Calculates the fine for an overdue journal.
     *
     * @param overdueDays number of days the journal is overdue
     * @return the fine amount in NIS (overdueDays × 15)
     */
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 15;
    }
}
