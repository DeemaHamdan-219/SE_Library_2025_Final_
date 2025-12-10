package org.example.library.strategies;

public class BookFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 10;  // 10 NIS per day
    }
}