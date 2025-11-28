package com.carousell.marketplace.repository;

public interface CategoryStatsRepository {
    void increment(String category);
    void decrement(String category);
    /**
     * Returns the category with maximum listing count.
     * If there are no categories, returns empty string.
     */
    String getTopCategory();
}
