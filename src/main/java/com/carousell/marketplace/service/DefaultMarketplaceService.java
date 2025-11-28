package com.carousell.marketplace.service;

import com.carousell.marketplace.domain.Listing;
import com.carousell.marketplace.domain.User;
import com.carousell.marketplace.repository.CategoryStatsRepository;
import com.carousell.marketplace.repository.ListingRepository;
import com.carousell.marketplace.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.*;

public class DefaultMarketplaceService implements MarketplaceService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final CategoryStatsRepository categoryStatsRepository;

    private int listingSeed = 100000;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public DefaultMarketplaceService(UserRepository userRepository,
                                     ListingRepository listingRepository,
                                     CategoryStatsRepository categoryStatsRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.categoryStatsRepository = categoryStatsRepository;
    }

    @Override
    public String registerUser(String username) {
        String canonical = username.toLowerCase();
        if (userRepository.existsByUsername(canonical)) {
            return "Error - user already existing";
        }
        userRepository.save(new User(canonical));
        return "Success";
    }

    @Override
    public String createListing(String username,
                                String title,
                                String description,
                                String price,
                                String category) {
        String canonicalUser = username.toLowerCase();
        if (!userRepository.existsByUsername(canonicalUser)) {
            return "Error - unknown user";
        }

        int priceValue;
        try {
            priceValue = Integer.parseInt(price);
        } catch (NumberFormatException ex) {
            // problem statement doesn't define this; treat as 0 or error
            priceValue = 0;
        }

        listingSeed++;
        String id = String.valueOf(listingSeed);

        Listing listing = new Listing(
                id,
                stripQuotes(title),
                stripQuotes(description),
                priceValue,
                stripQuotes(category),
                canonicalUser,
                new Date()
        );

        listingRepository.save(listing);
        categoryStatsRepository.increment(listing.getCategory());

        return id;
    }

    @Override
    public String getListing(String username, String listingId) {
        String canonicalUser = username.toLowerCase();
        if (!userRepository.existsByUsername(canonicalUser)) {
            return "Error - unknown user";
        }

        Optional<Listing> listingOpt = listingRepository.findById(listingId);
        if (!listingOpt.isPresent()) {
            return "Error - not found";
        }

        Listing listing = listingOpt.get();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        return listing.getTitle() + "|" +
                listing.getDescription() + "|" +
                listing.getPrice() + "|" +
                sdf.format(listing.getCreatedAt()) + "|" +
                listing.getCategory() + "|" +
                listing.getUsername();
    }

    @Override
    public String deleteListing(String username, String listingId) {
        String canonicalUser = username.toLowerCase();
        Optional<Listing> listingOpt = listingRepository.findById(listingId);

        if (!listingOpt.isPresent()) {
            return "Error - listing does not exist";
        }

        Listing listing = listingOpt.get();
        if (!listing.getUsername().equals(canonicalUser)) {
            return "Error - listing owner mismatch";
        }

        listingRepository.delete(listingId);
        categoryStatsRepository.decrement(listing.getCategory());

        return "Success";
    }

    @Override
    public List<String> getCategoryListings(String username,
                                            String category,
                                            String sortBy,
                                            String order) {
        String canonicalUser = username.toLowerCase();
        if (!userRepository.existsByUsername(canonicalUser)) {
            return Collections.singletonList("Error - unknown user");
        }

        String cleanedCategory = stripQuotes(category);
        List<Listing> listings = listingRepository.findByCategory(cleanedCategory);

        if (listings.isEmpty()) {
            return Collections.singletonList("Error - category not found");
        }

        final boolean asc = "asc".equalsIgnoreCase(order);
        final boolean sortByPrice = "sort_price".equalsIgnoreCase(sortBy);
        final boolean sortByTime = "sort_time".equalsIgnoreCase(sortBy);

        Collections.sort(listings, new Comparator<Listing>() {
            @Override
            public int compare(Listing o1, Listing o2) {
                if (sortByPrice) {
                    int diff = o1.getPrice() - o2.getPrice();
                    return asc ? diff : -diff;
                } else if (sortByTime) {
                    int diff = o1.getCreatedAt().compareTo(o2.getCreatedAt());
                    return asc ? diff : -diff;
                }
                return 0;
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        List<String> result = new ArrayList<String>();
        for (Listing listing : listings) {
            String line = listing.getTitle() + "|" +
                    listing.getDescription() + "|" +
                    listing.getPrice() + "|" +
                    sdf.format(listing.getCreatedAt());
            result.add(line);
        }
        return result;
    }

    @Override
    public String getTopCategory(String username) {
        String canonicalUser = username.toLowerCase();
        if (!userRepository.existsByUsername(canonicalUser)) {
            return "Error - unknown user";
        }
        String top = categoryStatsRepository.getTopCategory();
        if (top == null || top.isEmpty()) {
            // Spec does not define empty marketplace case; return empty string
            return "";
        }
        return top;
    }

    private String stripQuotes(String value) {
        if (value == null) {
            return null;
        }
        String v = value;
        if (v.length() >= 2 && v.startsWith("'") && v.endsWith("'")) {
            return v.substring(1, v.length() - 1);
        }
        return v;
    }
}
