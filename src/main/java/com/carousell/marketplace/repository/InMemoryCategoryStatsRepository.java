package com.carousell.marketplace.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCategoryStatsRepository implements CategoryStatsRepository {

    private final Map<String, Integer> categoryCounts =
            Collections.synchronizedMap(new HashMap<String, Integer>());

    @Override
    public void increment(String category) {
        Integer count = categoryCounts.get(category);
        if (count == null) {
            count = 0;
        }
        categoryCounts.put(category, count + 1);
    }

    @Override
    public void decrement(String category) {
        Integer count = categoryCounts.get(category);
        if (count == null) {
            return;
        }
        int newCount = count - 1;
        if (newCount <= 0) {
            categoryCounts.remove(category);
        } else {
            categoryCounts.put(category, newCount);
        }
    }

    @Override
    public String getTopCategory() {
        String topCategory = "";
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topCategory = entry.getKey();
            }
        }
        return topCategory;
    }
}
