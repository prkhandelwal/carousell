package com.carousell.marketplace.service;

import java.util.List;

public interface MarketplaceService {

    // REGISTER <username>
    String registerUser(String username);

    // CREATE_LISTING <username> <title> <description> <price> <category>
    String createListing(String username,
                         String title,
                         String description,
                         String price,
                         String category);

    // GET_LISTING <username> <listing_id>
    String getListing(String username, String listingId);

    // DELETE_LISTING <username> <listing_id>
    String deleteListing(String username, String listingId);

    // GET_CATEGORY <username> <category> {sort_price|sort_time} {asc|dsc}
    List<String> getCategoryListings(String username,
                                     String category,
                                     String sortBy,
                                     String order);

    // GET_TOP_CATEGORY <username>
    String getTopCategory(String username);
}
