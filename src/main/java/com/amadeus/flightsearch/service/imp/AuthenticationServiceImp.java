package com.amadeus.flightsearch.service.imp;

import com.amadeus.flightsearch.entity.User;
import com.amadeus.flightsearch.service.AuthenticationService;
import com.amadeus.flightsearch.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;
  @Override
  public Optional<User> login(String username, String password){
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              username,
              password
          )
      );
    } catch (BadCredentialsException exception) {
      return Optional.empty();
    }
    return userService.findUserByUsername(username);
  }
}
