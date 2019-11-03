package com.example.web;

import com.example.web.dto.UserRegistration;
import com.example.web.dto.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  //  private UserService userService;

  @PostMapping
  public UserResponse createUser(@RequestBody UserRegistration body) {
    return null;
  }
}
