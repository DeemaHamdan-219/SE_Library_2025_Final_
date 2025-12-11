package org.example.library.strategies;

/**
 * Fine calculation strategy for books.
 *
 * <p>This class implements {@link FineStrategy} and defines the rule
 * for calculating fines on overdue books. The fine is calculated as:</p>
 *
 * <pre>
 *     fine = overdueDays * 10
 * </pre>
 *
 * <p>Each overdue day costs 10 NIS.</p>
 *
 * @author Dima & Asma'a
 * @version 1.0
 */
public class BookFineStrategy implements FineStrategy {

    /**
     * Calculates the fine for a given number of overdue days.
     *
     * @param overdueDays number of days the book is overdue (must be >= 0)
     * @return fine amount in NIS
     */
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 10;  // 10 NIS per day
    }
}
