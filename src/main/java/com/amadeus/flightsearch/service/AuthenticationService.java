package com.amadeus.flightsearch.service;

import com.amadeus.flightsearch.entity.User;
import java.util.Optional;

public interface AuthenticationService {

  Optional<User> login(String username, String password);

}
