package com.carousell.marketplace.repository;

import com.carousell.marketplace.domain.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingRepository {
    void save(Listing listing);
    Optional<Listing> findById(String id);
    void delete(String id);
    List<Listing> findByCategory(String category);
}
