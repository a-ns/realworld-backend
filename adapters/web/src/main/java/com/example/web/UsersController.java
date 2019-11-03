package com.example.web;

import com.example.UserService;
import com.example.web.dto.UserLogin;
import com.example.web.dto.UserRegistration;
import com.example.web.dto.UserResponse;
import com.example.web.dto.UserWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

  private UserService userService;
  private UserWebMapper userMapper;

  @PostMapping
  public UserResponse createUser(@RequestBody UserRegistration body) {
    return userMapper.mapUserToResponse(
        this.userService.register(body.getUsername(), body.getEmail(), body.getPassword()));
  }

  @PostMapping("/login")
  public UserResponse login(@RequestBody UserLogin body) {
    return userMapper.mapUserToResponse(
        this.userService.login(body.getEmail(), body.getPassword()));
  }
}
