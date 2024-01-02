package com.amadeus.flightsearch.service.imp;

import com.amadeus.flightsearch.entity.User;
import com.amadeus.flightsearch.repository.UserRepository;
import com.amadeus.flightsearch.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;

  @Override
  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    userRepository.deleteUserById(userId);
  }

}
