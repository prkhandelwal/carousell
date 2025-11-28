package com.carousell.marketplace.repository;

import com.carousell.marketplace.domain.User;

public interface UserRepository {
    boolean existsByUsername(String username);
    void save(User user);
}
