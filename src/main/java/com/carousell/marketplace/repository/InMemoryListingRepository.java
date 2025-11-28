package com.carousell.marketplace.repository;

import com.carousell.marketplace.domain.Listing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryListingRepository implements ListingRepository {

    private final Map<String, Listing> listings =
            Collections.synchronizedMap(new HashMap<String, Listing>());

    @Override
    public void save(Listing listing) {
        listings.put(listing.getId(), listing);
    }

    @Override
    public Optional<Listing> findById(String id) {
        Listing listing = listings.get(id);
        return listing == null ? Optional.<Listing>empty()
                : Optional.of(listing);
    }

    @Override
    public void delete(String id) {
        listings.remove(id);
    }

    @Override
    public List<Listing> findByCategory(String category) {
        List<Listing> result = new ArrayList<Listing>();
        for (Listing listing : listings.values()) {
            if (listing.getCategory().equals(category)) {
                result.add(listing);
            }
        }
        return result;
    }
}
