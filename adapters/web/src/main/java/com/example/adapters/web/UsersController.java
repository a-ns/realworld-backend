package com.example.adapters.web;

import com.example.adapters.web.dto.UserLogin;
import com.example.adapters.web.dto.UserRegistration;
import com.example.adapters.web.dto.UserResponse;
import com.example.adapters.web.dto.UserWebMapper;
import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.UserRegistrationCommand;
import com.example.application.domain.ports.in.LoginUserUseCase;
import com.example.application.domain.ports.in.RegisterUserUseCase;
import javax.security.auth.login.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

  private RegisterUserUseCase registerUserUseCase;
  private LoginUserUseCase loginUserUseCase;
  private UserWebMapper userMapper;

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRegistration body) {
    try {

      return ResponseEntity.ok(
          userMapper.mapUserToResponse(
              this.registerUserUseCase.registerUser(
                  UserRegistrationCommand.builder()
                      .email(body.getUser().getEmail())
                      .password(body.getUser().getPassword())
                      .username(body.getUser().getUsername())
                      .build())));
    } catch (EmailAreadyTakenException | UsernameAlreadyTakenException e) {
      return ResponseEntity.status(204).build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponse> login(@RequestBody UserLogin body) {
    try {
      return ResponseEntity.ok()
          .body(
              userMapper.mapUserToResponse(
                  this.loginUserUseCase.login(
                      body.getUser().getEmail(), body.getUser().getPassword())));
    } catch (LoginException e) {
      return ResponseEntity.status(401).build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
}
