package org.example.library.strategies;

public class CDFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 20;  // 20 NIS per day
    }
}
