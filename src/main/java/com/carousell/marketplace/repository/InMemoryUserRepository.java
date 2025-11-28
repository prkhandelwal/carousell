package com.carousell.marketplace.repository;

import com.carousell.marketplace.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> users =
            Collections.synchronizedMap(new HashMap<String, User>());

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}
