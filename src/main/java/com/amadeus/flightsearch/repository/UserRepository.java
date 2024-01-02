package com.amadeus.flightsearch.repository;

import com.amadeus.flightsearch.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  UserDetails findUserByUsername(String username);

  User save(User user);

  void deleteUserById(Long userId);

}
