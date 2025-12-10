package org.example.library.strategies;

public class JournalFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 15;
    }
}