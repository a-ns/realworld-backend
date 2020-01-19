package com.example.adapters.web;

import com.example.adapters.web.dto.input.UserLoginPayload;
import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.mapper.UserWebMapper;
import com.example.adapters.web.dto.output.GetUserResponse;
import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
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
  public ResponseEntity<GetUserResponse> createUser(@RequestBody UserRegistrationPayload body) {
    try {

      return ResponseEntity.ok(
          userMapper.mapUserToResponse(
              this.registerUserUseCase.registerUser(
                  new RegisterUserUseCase.UserRegistrationCommand(
                      body.getUser().getUsername(),
                      body.getUser().getEmail(),
                      body.getUser().getPassword()))));
    } catch (EmailAreadyTakenException | UsernameAlreadyTakenException e) {
      return ResponseEntity.status(204).build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<GetUserResponse> login(@RequestBody UserLoginPayload body) {
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
