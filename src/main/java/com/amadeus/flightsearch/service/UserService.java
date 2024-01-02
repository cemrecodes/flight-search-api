package com.amadeus.flightsearch.service;

import com.amadeus.flightsearch.entity.User;
import java.util.Optional;

public interface UserService {

  Optional<User> findUserByUsername(String username);

  User saveUser(User user);

  void deleteUser(Long userId);
}
