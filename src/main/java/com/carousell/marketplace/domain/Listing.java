package com.carousell.marketplace.domain;

import java.util.Date;

public class Listing {
    private final String id;
    private final String title;
    private final String description;
    private final int price;
    private final String category;
    private final String username; // owner username (canonical)
    private final Date createdAt;

    public Listing(String id, String title, String description, int price,
                   String category, String username, Date createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
