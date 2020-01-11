package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.RegisterUserUseCase;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import com.example.application.domain.ports.out.SaveUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class RegisterUserService implements RegisterUserUseCase {

  private final AuthPort authService;
  private final GetUserPort getUserPort;
  private final SaveUserPort saveUserPort;

  public User registerUser(UserRegistrationCommand registrant) throws ExistingUserFoundException {
    this.getUserPort
        .getUserByUsername(registrant.getUsername())
        .ifPresent(
            (existing) -> {
              throw new UsernameAlreadyTakenException();
            });
    this.getUserPort
        .getUserByEmail(registrant.getEmail())
        .ifPresent(
            (existing) -> {
              throw new EmailAreadyTakenException();
            });
    User user =
        User.builder()
            .username(registrant.getUsername())
            .email(registrant.getEmail())
            .password(authService.encrypt(registrant.getPassword()))
            .build();
    user = this.saveUserPort.saveUser(user);
    String token = this.authService.generateToken(user);
    user.setToken(token);
    return user;
  }
}
