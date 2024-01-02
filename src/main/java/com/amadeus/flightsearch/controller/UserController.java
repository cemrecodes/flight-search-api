package com.amadeus.flightsearch.controller;

import com.amadeus.flightsearch.dto.UserDto;
import com.amadeus.flightsearch.entity.User;
import com.amadeus.flightsearch.service.AuthenticationService;
import com.amadeus.flightsearch.service.JwtService;
import com.amadeus.flightsearch.service.UserService;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final JwtService jwtService;

  private final AuthenticationService authService;

  private final PasswordEncoder passwordEncoder;

  @PostMapping
  public ResponseEntity register(@RequestBody UserDto userDto){
    if(userService.findUserByUsername(userDto.getUsername()).isPresent()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is taken");
    }
    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(userDto.getPassword());
    user = userService.saveUser(user);
    return ResponseEntity.ok(Map.of("token", jwtService.generateToken(user)));
  }

  @PostMapping(value = "/login")
  public ResponseEntity login(@RequestBody UserDto userDto){
    Optional<User> user = authService.login(userDto.getUsername(), userDto.getPassword());
    if( user.isPresent() ){
      return ResponseEntity.ok(Map.of("token", jwtService.generateToken(user.get())));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PutMapping()
  public ResponseEntity updateUser(@RequestBody UserDto userDto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
      String username = userDetails.getUsername();
      Optional<User> user = userService.findUserByUsername(username);
      if( user.isPresent() ){
        user.get().setUsername(userDto.getUsername());
        user.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.saveUser(user.get());
        return ResponseEntity.ok().build();
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @DeleteMapping()
  public ResponseEntity<Void> deleteUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
      String username = userDetails.getUsername();
      Optional<User> user = userService.findUserByUsername(username);
      if( user.isPresent() ){
        userService.deleteUser(user.get().getId());
        return ResponseEntity.noContent().build();
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}
